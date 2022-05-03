package com.example.userservice.userMenager.business.service;

import com.example.userservice.business.service.JwtTokenNonUserProvider;
import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.request.EmployeeRequest;
import com.example.userservice.userMenager.api.response.BranchView;
import com.example.userservice.userMenager.business.exception.branch.BranchNotFoundException;
import com.example.userservice.userMenager.business.exception.role.RoleNotFoundException;
import com.example.userservice.userMenager.business.exception.user.UserMailExistsException;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.*;
import com.example.userservice.userMenager.data.repository.*;
import com.example.userservice.userMenager.feignClient.GeneralInformationServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class EmployeeService {
    private UserRepo userRepo;

    private AddressRepo addressRepo;

    private BranchRepo branchRepo;

    private EmployeeRepo employeeRepo;

    private PasswordEncoder passwordEncoder;


    private RoleRepo roleRepo;

    private GeneralInformationServiceFeignClient generalInformationServiceFeignClient;

    @Transactional
    public boolean addEmployee(EmployeeRequest employeeRequest) {

        isEmailValid(employeeRequest.getEmail());

        BranchView branchView = generalInformationServiceFeignClient.getBranch(employeeRequest.getBranchId());
        if (branchView == null) throw new BranchNotFoundException(employeeRequest.getBranchId());

        Employee employee = Employee.builder()
                .employeeDate(employeeRequest.getEmployeeDate())
                .build();

        Branch branch = Branch.builder()
                .country("Polska")
                .latitude(branchView.getLatitude())
                .longitude(branchView.getLongitude())
                .phone(branchView.getPhone())
                .city(branchView.getCity())
                .street(branchView.getStreet())
                .zipCode(branchView.getZipCode())
                .build();

        Role adminRole = roleRepo.findByRoleIgnoreCase(RoleEnum.ADMIN.name()).orElseThrow(() -> new RoleNotFoundException(RoleEnum.ADMIN.name()));
        Role employeeRole = roleRepo.findByRoleIgnoreCase(RoleEnum.EMPLOYEE.name()).orElseThrow(() -> new RoleNotFoundException(RoleEnum.EMPLOYEE.name()));
        Role role = employeeRequest.getIsAdmin() ? adminRole : employeeRole;


        Address address = Address.builder()
                .zipCode(employeeRequest.getAddressView().getZipCode())
                .street(employeeRequest.getAddressView().getStreet())
                .city(employeeRequest.getAddressView().getCity())
                .houseNr(employeeRequest.getAddressView().getHouseNr())
                .build();

        User user = User.builder()
                .active(true)
                .email(employeeRequest.getEmail())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .phone(employeeRequest.getPhone())
                .role(role)
                .birthday(employeeRequest.getBirthDay())
                .branch(branch)
                .address(address)
                .employee(employee)
                .build();
        address.setUser(user);
        branch.setUser(user);
        employee.setUser(user);

        addressRepo.save(address);
        employeeRepo.save(employee);
        branchRepo.save(branch);
        userRepo.save(user);

        return true;


    }


    private void isEmailValid(String email) {
        userRepo.findByEmail(email).ifPresent(user -> {
            throw new UserMailExistsException(email);
        });
    }
}
