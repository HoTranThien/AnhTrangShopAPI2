package api.anhtrangapiv2.service.email;


public interface IEmaiService {
    void sendEmail(String toEmail, String subject, String body) throws Exception;
}
