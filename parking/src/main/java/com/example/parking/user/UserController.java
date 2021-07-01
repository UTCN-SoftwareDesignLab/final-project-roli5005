package com.example.parking.user;

import com.example.parking.report.ReportService;
import com.example.parking.user.dto.UserCreateDTO;
import com.example.parking.user.dto.UserMinimalDTO;
import com.example.parking.user.validator.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.parking.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReportService reportService;

    @GetMapping
    public List<UserMinimalDTO> allUsers(){
        return userService.allUsersMinimal();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable long id){userService.delete(id);}

    @PatchMapping
    public String edit(@RequestBody UserCreateDTO userCreateDto) {
        Notification<UserCreateDTO> notification= userService.edit(userCreateDto);
        if(notification.hasErrors()) return notification.getFormattedErrors();
        else return "Success!";
    }

    @PostMapping
    public String create(@RequestBody UserCreateDTO userCreateDto){
        Notification<UserCreateDTO> notification= userService.create(userCreateDto);
        if(notification.hasErrors()) return notification.getFormattedErrors();
        else return "Success!"; }

    @GetMapping(REPORT + "/{year}/{month}")
    public @ResponseBody byte[] getReport(@PathVariable int year,@PathVariable int month) throws IOException {

        String file = reportService.getReport(year,month);
        if(file!=null)
        try {
            return Files.readAllBytes(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
