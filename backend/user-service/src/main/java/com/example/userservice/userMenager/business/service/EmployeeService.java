package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.mapper.UserMapper;
import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.DetailUserView;
import com.example.userservice.userMenager.business.exception.address.AddressNotFoundException;
import com.example.userservice.userMenager.business.exception.role.RoleNotFoundException;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.Address;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.AddressRepo;
import com.example.userservice.userMenager.data.repository.RoleRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private PasswordEncoder passwordEncoder;
    private RoleRepo roleRepo;



    public List<DetailUserView> getAllActiveClients() {
        List<User> allActiveClient = userRepo.findAllByRole_RoleAndActive(RoleEnum.CLIENT.name(),true);
        return allActiveClient.stream()
                .map(UserMapper::mapDataToDetailedResponse)
                .collect(Collectors.toList());
    }


    public void changeClientStatusOnInactive(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(" with id "+id));
        user.setActive(false);
        userRepo.save(user);
    }

    public List<DetailUserView> getAllClients() {
        List<User> allClients = userRepo.findAllByRole_Role(RoleEnum.CLIENT.name());
        return allClients.stream()
                .map(UserMapper::mapDataToDetailedResponse)
                .collect(Collectors.toList());
    }

    public void activateClient(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(" with id "+id));
        if(RoleEnum.CLIENT.name().equalsIgnoreCase(user.getRole().getRole())) {
            user.setActive(true);
            userRepo.save(user);
        }
    }

    public void updateClient(EmployeeUpdateRequest employeeUpdateRequest) {
        User user = userRepo.findByUserIdAndRole_Role(employeeUpdateRequest.getUserId(), RoleEnum.CLIENT.name()).orElseThrow(() ->
                new UserNotFoundException(" with id "+employeeUpdateRequest.getUserId()+" and role "+  RoleEnum.CLIENT.name()));
        user.setEmail(employeeUpdateRequest.getEmail());
        user.setFirstName(employeeUpdateRequest.getFirstName());
        user.setLastName(employeeUpdateRequest.getLastName());
        user.setPhone(employeeUpdateRequest.getPhone());
        user.setBirthday(employeeUpdateRequest.getBirthDay());
        Role role = null;

        if(employeeUpdateRequest.getIsAdmin()) role = roleRepo.findByRoleIgnoreCase(RoleEnum.ADMIN.name())
                .orElseThrow(()-> new RoleNotFoundException(RoleEnum.ADMIN.name()));

        else role = roleRepo.findByRoleIgnoreCase(RoleEnum.CLIENT.name())
                .orElseThrow(()-> new RoleNotFoundException(RoleEnum.CLIENT.name()));

        user.setRole(role);

        if(!StringUtils.isBlank(employeeUpdateRequest.getPassword()))
            user.setPassword(passwordEncoder.encode(employeeUpdateRequest.getPassword()));


        Address address = addressRepo.findByUser(user).orElseThrow(()->new AddressNotFoundException("user id: "+user.getUserId()));

        address.setZipCode(employeeUpdateRequest.getAddressView().getZipCode());
        address.setStreet(employeeUpdateRequest.getAddressView().getStreet());
        address.setCity(employeeUpdateRequest.getAddressView().getCity());
        address.setHouseNr(employeeUpdateRequest.getAddressView().getHouseNr());

        addressRepo.save(address);
        userRepo.save(user);
    }
}
