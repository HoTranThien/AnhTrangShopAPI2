package api.anhtrangapiv2.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.models.User;
import api.anhtrangapiv2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmaiService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    
    @Value("${spring.mail.username}")
    private String email;
    @Override
    public void sendEmail(String toEmail, String subject, String body) throws Exception{
        if(!userRepository.existsByEmail(email)){
            throw new RuntimeException("The email is incorrect");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
