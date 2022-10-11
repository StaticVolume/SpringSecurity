package ru.springBoot311.SpringBoot.sources.conftroller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springBoot311.SpringBoot.sources.model.Role;
import ru.springBoot311.SpringBoot.sources.model.User;
import ru.springBoot311.SpringBoot.sources.security.MyUserDetails;
import ru.springBoot311.SpringBoot.sources.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getStartPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        for (Role role : myUserDetails.getUser().getRoles()) {
            if(role.getName().contains("ADMIN")) {
                return "redirect:/admin";
            }
        }
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String userPanel(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("user", myUserDetails.getUser());
            return "/userInfo";
        }

    @GetMapping("/admin")
    public String adminPanel() {
        return "/adminPanel";
    }
        @GetMapping("/admin/allUsers")
        public String printAllUsers(Model model) {
            model.addAttribute("users_list", userService.getAllUsers());
            return "/allUsers";
        }

        @RequestMapping("admin/createUser")
        public String createUserForm(Model model){
            model.addAttribute("user",new User());
            return "/createUser";
        }

        @PostMapping("admin/createUser")
        public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
            if(bindingResult.hasErrors()) {
                return "createUser";
            }
            userService.addUser(user);
            return "redirect:/admin/allUsers";
        }

        @GetMapping("admin/allUsers/{id}/edit")
        public String editUserForm(Model model, @PathVariable("id") long id) {
            model.addAttribute("user", userService.getUser(id));
            return "/editUser";
        }

        @PatchMapping("admin/allUsers/{id}")
        public String updateUser(@ModelAttribute("user") User user,@PathVariable("id") long id ) {
            userService.updateUser(user);
            return "redirect:/admin/allUsers";
        }

        @DeleteMapping("admin/allUsers/delete/{id}")
        public String deleteUser( @PathVariable("id") long id) {
            userService.deleteUser(id);
            return "redirect:/admin/allUsers";
        }
}
