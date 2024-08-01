package bg.softuni.finalproject.web;


import bg.softuni.finalproject.Entity.Message;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.MessageRepository;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.MessageDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AdminController {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AdminController(MessageRepository messageRepository, ModelMapper modelMapper, UserService userService) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/admin-all/contact")
    public String showContactPage(Model model) {
        model.addAttribute("messageDTO", new MessageDTO());
        return "/admin-all/contact";
    }

    @PostMapping("/admin-all/contact")
    public String handleContactForm(@Valid @ModelAttribute MessageDTO messageDTO,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/admin-all/contact";
        }

        Message newMessage = modelMapper.map(messageDTO, Message.class);
        newMessage.setSentAt(LocalDateTime.now());
        newMessage.setAnswered(false);

        messageRepository.save(newMessage);

        redirectAttributes.addFlashAttribute("success", "Your message has been sent successfully!");
        return "redirect:/admin-all/contact";
    }

    @GetMapping("/admin-all/messages")
    public String showMessagesPage(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        List<Message> messagesList = messageRepository.findAllOrderByAnswered();
        model.addAttribute("messagesList", messagesList);
        return "/admin-all/messages";
    }

    @PostMapping("/admin-all/messages/delete")
    public String deleteMessage(@RequestParam Long id) {
        messageRepository.deleteById(id);
        return "redirect:/admin-all/messages";
    }


    @PostMapping("/admin-all/messages/toggle-answered")
    public String toggleMessageAnsweredStatus(@RequestParam Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
        message.setAnswered(!message.isAnswered());
        messageRepository.save(message);
        return "redirect:/admin-all/messages";
    }
}

