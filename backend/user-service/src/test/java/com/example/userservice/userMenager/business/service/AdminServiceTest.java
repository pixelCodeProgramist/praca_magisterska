package com.example.userservice.userMenager.business.service;


import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.mapper.BranchMapper;
import com.example.userservice.userMenager.api.request.AddressRequest;
import com.example.userservice.userMenager.api.request.EmployeeRequest;
import com.example.userservice.userMenager.api.request.EmployeeUpdateRequest;
import com.example.userservice.userMenager.api.response.BranchView;
import com.example.userservice.userMenager.api.response.EmployeeUserView;
import com.example.userservice.userMenager.data.entity.Branch;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.*;
import com.google.common.net.HttpHeaders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {
    @Autowired
    BranchRepo branchRepo;

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    JwtTokenProvider jwtTokenProvider;



    @Before
    public void init() {
        Role admin = Role.builder()
                .role(RoleEnum.ADMIN.name())
                .build();
        Role client = Role.builder()
                .role(RoleEnum.CLIENT.name())
                .build();
        Role employee = Role.builder()
                .role(RoleEnum.EMPLOYEE.name())
                .build();
        roleRepo.save(admin);
        roleRepo.save(client);
        roleRepo.save(employee);
    }

    @Test
    public void can_add_new_employee() {
        List<User> employeesBefore =
                Stream.concat(
                                userRepo.findAllByRole_Role(RoleEnum.EMPLOYEE.name()).stream(),
                                userRepo.findAllByRole_Role(RoleEnum.ADMIN.name()).stream())
                        .collect(Collectors.toList());

        Branch branch =  Branch.builder()
                .zipCode("11111")
                .street("")
                .city("")
                .longitude(1)
                .latitude(1)
                .phone("")
                .build();

        branch = branchRepo.save(branch);

        AddressRequest addressRequest = AddressRequest.builder()
                .zipCode("11111")
                .street("TestStreet")
                .houseNr("1")
                .city("TestCity")
                .build();

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .addressView(addressRequest)
                .employeeDate(new Date())
                .isAdmin(false)
                .email("test@o2.pl")
                .birthDay(new Date())
                .firstName("Test first name")
                .lastName("Test last name")
                .password("123456qwertyASDFGH")
                .phone(new BigInteger("123456789"))
                .branchId(branch.getBranchId())
                .build();

        adminService.addEmployee(employeeRequest, true);

        List<User> employeesAfter =
                Stream.concat(
                        userRepo.findAllByRole_Role(RoleEnum.EMPLOYEE.name()).stream(),
                                userRepo.findAllByRole_Role(RoleEnum.ADMIN.name()).stream())
                .collect(Collectors.toList());

        Assertions.assertTrue(employeesAfter.size()>employeesBefore.size());

        Optional<User> user = employeesAfter.stream().filter(employee -> employeeRequest.getEmail().equals(employee.getEmail()))
                .findFirst();

        Assertions.assertTrue(user.isPresent());
    }

    private void prepare() {

        finish();
        init();

        Branch branch =  Branch.builder()
                .zipCode("11111")
                .street("")
                .city("")
                .longitude(1)
                .latitude(1)
                .phone("")
                .build();

        branch = branchRepo.save(branch);

        AddressRequest addressRequest = AddressRequest.builder()
                .zipCode("11111")
                .street("TestStreet")
                .houseNr("1")
                .city("TestCity")
                .build();

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .addressView(addressRequest)
                .employeeDate(new Date())
                .isAdmin(false)
                .email("testMail@o2.pl")
                .birthDay(new Date())
                .firstName("Test first name")
                .lastName("Test last name")
                .password("123456qwertyASDFGH")
                .phone(new BigInteger("123456789"))
                .branchId(branch.getBranchId())
                .build();

        EmployeeRequest employeeRequest2 = EmployeeRequest.builder()
                .addressView(addressRequest)
                .employeeDate(new Date())
                .isAdmin(true)
                .email("testMail2@o2.pl")
                .birthDay(new Date())
                .firstName("Test first name")
                .lastName("Test last name")
                .password("123456qwertyASDFGH")
                .phone(new BigInteger("123456789"))
                .branchId(branch.getBranchId())
                .build();

        adminService.addEmployee(employeeRequest, true);
        adminService.addEmployee(employeeRequest2, true);
    }

    @Test
    public void can_get_all_employees() {
        prepare();
        User user = userRepo.findAllByRole_Role(RoleEnum.ADMIN.name()).get(0);

        String token = issueTokenForUser(user);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/detailUser");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        final List<EmployeeUserView>[] employeeUserViews = new List[]{new ArrayList<>()};
        Assertions.assertDoesNotThrow(()-> employeeUserViews[0] = adminService.getAllEmployees(request));
        Assertions.assertEquals(1, employeeUserViews[0].size());
    }

    @Test
    public void can_change_employee_status() {
        prepare();
        User user = userRepo.findAllByRole_Role(RoleEnum.EMPLOYEE.name()).get(0);
        adminService.changeEmployeeStatusOnInactive(user.getUserId());
        user = userRepo.findById(user.getUserId()).get();
        Assertions.assertFalse(user.isActive());
    }

    @Test
    public void can_update_employee() {
        prepare();
        User user = userRepo.findAllByRole_Role(RoleEnum.EMPLOYEE.name()).get(0);
        AddressRequest addressRequest = AddressRequest.builder()
                .city(user.getAddress().getCity())
                .houseNr(user.getAddress().getHouseNr())
                .street(user.getAddress().getStreet())
                .zipCode(user.getAddress().getZipCode())
                .build();

        EmployeeUpdateRequest employeeUpdateRequest = EmployeeUpdateRequest.builder()
                .isAdmin(user.getRole().getRole().equalsIgnoreCase(RoleEnum.ADMIN.name()) ||
                        (user.getRole().getRole().equalsIgnoreCase(RoleEnum.EMPLOYEE.name())))
                .userId(user.getUserId())
                .phone(user.getPhone())
                .password(user.getPassword())
                .lastName(user.getLastName())
                .firstName("Test for firstname")
                .email(user.getEmail())
                .birthDay(user.getBirthday())
                .addressView(addressRequest)
                .employeeDate(user.getEmployee().getEmployeeDate())
                .branchView(BranchMapper.mapBranchToData(user.getBranch()))
                .build();

        adminService.updateEmployee(employeeUpdateRequest);
        user = userRepo.findById(user.getUserId()).get();
        Assertions.assertEquals("Test for firstname", user.getFirstName());

    }

    @After
    public void finish() {
        addressRepo.deleteAll();
        employeeRepo.deleteAll();
        branchRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();

    }

    private String issueTokenForUser(User user) {
        return jwtTokenProvider.generateToken(user);
    }
}