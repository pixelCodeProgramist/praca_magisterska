package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.business.exception.address.AddressNotFoundException;
import org.apache.commons.lang3.StringUtils;
import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.mapper.EmployeeMapper;
import com.example.userservice.userMenager.api.request.EmployeeRequest;
import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.BranchView;
import com.example.userservice.userMenager.api.response.EmployeeUserView;
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

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AdminService {
    private UserRepo userRepo;

    private AddressRepo addressRepo;

    private BranchRepo branchRepo;

    private JwtTokenProvider tokenProvider;

    private EmployeeRepo employeeRepo;

    private PasswordEncoder passwordEncoder;


    private RoleRepo roleRepo;

    private GeneralInformationServiceFeignClient generalInformationServiceFeignClient;

    @Transactional
    public boolean addEmployee(EmployeeRequest employeeRequest, boolean isTest) {

        isEmailValid(employeeRequest.getEmail());
        BranchView branchView = null;
        if(!isTest)
            branchView = generalInformationServiceFeignClient.getBranch(employeeRequest.getBranchId());
        else
            branchView = BranchView.builder()
                    .latitude(1)
                    .longitude(1)
                    .phone("")
                    .city("")
                    .street("")
                    .zipCode("")
                    .build();

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

    public List<EmployeeUserView> getAllEmployees(HttpServletRequest httpServletRequest) {
        List<User> allAdmin = userRepo.findAllByRole_RoleAndActive(RoleEnum.ADMIN.name(),true);
        List<User> allEmployee = userRepo.findAllByRole_RoleAndActive(RoleEnum.EMPLOYEE.name(),true);
        List<User> allAdminEmployee = Stream.concat(allAdmin.stream(), allEmployee.stream())
                .collect(Collectors.toList());
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null) {
            token = token.substring(7);
            Long userId = tokenProvider.extractUserId(token);
            User user = userRepo.findById(userId).orElseThrow(() ->
                    new UserNotFoundException("with id: " + userId));
            allAdminEmployee = allAdminEmployee.stream()
                    .filter(u->u.getUserId().longValue()!=userId.longValue())
                    .collect(Collectors.toList());

        }


        return allAdminEmployee.stream()
                .map(EmployeeMapper::mapUserToData)
                .collect(Collectors.toList());
    }

    public void changeEmployeeStatusOnInactive(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(" with id "+id));
        user.setActive(false);
        userRepo.save(user);
    }

    public void updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        User user = userRepo.findById(employeeUpdateRequest.getUserId()).orElseThrow(() ->
                new UserNotFoundException(" with id "+employeeUpdateRequest.getUserId()));
        user.setEmail(employeeUpdateRequest.getEmail());
        user.setFirstName(employeeUpdateRequest.getFirstName());
        user.setLastName(employeeUpdateRequest.getLastName());
        user.setPhone(employeeUpdateRequest.getPhone());
        user.setBirthday(employeeUpdateRequest.getBirthDay());
        Role role = null;

        if(employeeUpdateRequest.getIsAdmin()) role = roleRepo.findByRoleIgnoreCase(RoleEnum.ADMIN.name())
                .orElseThrow(()-> new RoleNotFoundException(RoleEnum.ADMIN.name()));

        else role = roleRepo.findByRoleIgnoreCase(RoleEnum.EMPLOYEE.name())
                .orElseThrow(()-> new RoleNotFoundException(RoleEnum.EMPLOYEE.name()));

        user.setRole(role);

        if(!StringUtils.isBlank(employeeUpdateRequest.getPassword()))
            user.setPassword(passwordEncoder.encode(employeeUpdateRequest.getPassword()));

        Employee employee = employeeRepo.findByUser(user)
                .orElseThrow(()-> new UserNotFoundException("with user id" + user.getUserId()));

        employee.setEmployeeDate(employeeUpdateRequest.getEmployeeDate());

        Branch branch = branchRepo.findById(employeeUpdateRequest.getBranchView().getBranchId())
                .orElseThrow(() -> new BranchNotFoundException(employeeUpdateRequest.getBranchView().getBranchId()));

        branch.setUser(user);

        Address address = addressRepo.findByUser(user).orElseThrow(()->new AddressNotFoundException("user id: "+user.getUserId()));

        address.setZipCode(employeeUpdateRequest.getAddressView().getZipCode());
        address.setStreet(employeeUpdateRequest.getAddressView().getStreet());
        address.setCity(employeeUpdateRequest.getAddressView().getCity());
        address.setHouseNr(employeeUpdateRequest.getAddressView().getHouseNr());

        employeeRepo.save(employee);
        branchRepo.save(branch);
        addressRepo.save(address);
        userRepo.save(user);


    }
}
