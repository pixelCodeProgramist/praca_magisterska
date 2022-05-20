package com.example.userservice.userMenager.business.service;


import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.request.AddressRequest;
import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.DetailUserView;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.Address;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.AddressRepo;
import com.example.userservice.userMenager.data.repository.RoleRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Before
    public void init() {
        Role role = Role.builder()
                .role(RoleEnum.CLIENT.name())
                .build();
        role = this.roleRepo.save(role);

        for(int i=0;i<2;i++){
            Address address = Address.builder()
                    .zipCode("11111")
                    .street("testStreet")
                    .city("testCity")
                    .houseNr("1")
                    .build();

            User user = User.builder()
                    .active(i==0)
                    .email("userTestMail"+(i+1)+"@o2.pl")
                    .role(role)
                    .address(address)
                    .password("")
                    .build();
            address.setUser(user);
            this.addressRepo.save(address);
            this.userRepo.save(user);
        }
    }

    @After
    public void finish() {
        addressRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
    }

    @Test
    public void should_get_all_active_or_inactive_clients() {
        List<DetailUserView> allActiveClients = employeeService.getAllActiveClients();
        List<DetailUserView> allClients = employeeService.getAllClients();
        Assertions.assertEquals(1, allActiveClients.size());
        Assertions.assertTrue(allActiveClients.get(0).getActive());
        Assertions.assertEquals(2, allClients.size());
    }

    @Test
    public void can_change_client_status() {
        List<DetailUserView> allActiveClients = employeeService.getAllActiveClients();
        DetailUserView client = allActiveClients.get(0);
        employeeService.changeClientStatusOnInactive(client.getUserId());
        User user = userRepo.findById(client.getUserId()).get();
        Assertions.assertFalse(user.isActive());
        employeeService.activateClient(client.getUserId());
        user = userRepo.findById(client.getUserId()).get();
        Assertions.assertTrue(user.isActive());
        Assertions.assertThrows(UserNotFoundException.class, () ->employeeService.changeClientStatusOnInactive(-1L));
    }

    @Test
    public void can_update_client() {
        List<DetailUserView> allActiveClients = employeeService.getAllActiveClients();
        DetailUserView userDetails = allActiveClients.get(0);
        User user = userRepo.findById(userDetails.getUserId()).get();
        AddressRequest addressRequest = AddressRequest.builder()
                .city(userDetails.getAddressView().getCity())
                .houseNr(userDetails.getAddressView().getHouseNr())
                .street(userDetails.getAddressView().getStreet())
                .zipCode(userDetails.getAddressView().getZipCode())
                .build();
        EmployeeUpdateRequest employeeUpdateRequest = EmployeeUpdateRequest.builder()
                .userId(user.getUserId())
                .addressView(addressRequest)
                .birthDay(userDetails.getBirthDay())
                .email(userDetails.getEmail())
                .firstName(userDetails.getFirstName())
                .lastName("Testowe nazwisko update")
                .password(user.getPassword())
                .phone(user.getPhone())
                .isAdmin(false)
                .build();
        employeeService.updateClient(employeeUpdateRequest);
        user = userRepo.findById(userDetails.getUserId()).get();
        Assertions.assertEquals("Testowe nazwisko update", user.getLastName());
        employeeUpdateRequest.setUserId(-1L);
        Assertions.assertThrows(UserNotFoundException.class, () ->employeeService.updateClient(employeeUpdateRequest));
        employeeUpdateRequest.setUserId(user.getUserId());
    }
}