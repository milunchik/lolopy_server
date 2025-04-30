package lolopy.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  public void sendBookConfirmationEmail(String to, String country, String city, String date, String capacity,
      String accommodation, String transport) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setSubject("Підтвердження бронювання подорожі");

      String htmlContent = String.format(
          """
              <!DOCTYPE html>
              <html lang="en">
              <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Trip Booking Confirmation</title>
                <style>
                  body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #fff9f4;
                    color: #3a3a3a;
                  }

                  .emailWrapper {
                    max-width: 660px;
                    margin: 50px auto;
                    background-color: #fff9f4;
                    border-radius: 12px;
                    box-shadow: 0 12px 30px rgba(0, 0, 0, 0.08);
                    overflow: hidden;
                    border: 1px solid #e0ddd7;
                  }

                  .banner img {
                    width: 100%%;
                    height: auto;
                    display: block;
                    object-fit: cover;
                  }

                  .titleContainer {
                    background: linear-gradient(90deg, #0097a0, #b87333);
                    color: #ffffff;
                    padding: 35px 30px;
                    text-align: center;
                  }

                  .titleContainer h1 {
                    margin: 0;
                    font-size: 26px;
                    font-weight: 600;
                    letter-spacing: 0.5px;
                  }

                  .tripInfoContainer {
                    padding: 35px 30px;
                    background-color: #f5f3ef;
                  }

                  .tripInfoContainer h2 {
                    font-size: 20px;
                    color: #0097a0;
                    margin-top: 0;
                    margin-bottom: 20px;
                    font-weight: 600;
                  }

                  .tripInfoContainer p {
                    margin: 12px 0;
                    font-size: 16px;
                    line-height: 1.6;
                    color: #3a3a3a;
                  }

                  .tripInfoContainer p strong {
                    color: #007680;
                  }

                  .companyInfo {
                    background-color: #faf7f3;
                    padding: 28px 30px;
                    font-size: 14px;
                    text-align: center;
                    border-top: 1px solid #e0ddd7;
                  }

                  .companyInfo img {
                    width: 22%%;
                    margin-bottom: 12px;
                  }

                  .companyInfo strong {
                    display: block;
                    font-size: 15px;
                    color: #222;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                    margin-bottom: 5px;
                  }

                  .companyInfo p {
                    margin: 6px 0;
                  }

                  .companyInfo a {
                    color: #3a3a3a;
                    text-decoration: none;
                  }

                  .companyInfo small {
                    display: block;
                    margin-top: 16px;
                    font-size: 12px;
                    color: #999;
                  }

                  @media (max-width: 600px) {
                    .emailWrapper {
                      margin: 20px;
                    }

                    .titleContainer h1 {
                      font-size: 22px;
                    }

                    .tripInfoContainer h2 {
                      font-size: 18px;
                    }

                    .tripInfoContainer p,
                    .companyInfo {
                      font-size: 14px;
                    }

                    .companyInfo small {
                      font-size: 11px;
                    }
                  }
                </style>
              </head>
              <body>
                <div class="emailWrapper">
                  <div class="banner">
                    <img src="https://i.ibb.co/NgsXshZ6/travel-stickers-badge-set-vector.png" alt="Travel Banner" />
                  </div>
                  <div class="titleContainer">
                    <h1>Your Trip Has Been Successfully Booked</h1>
                  </div>
                  <div class="tripInfoContainer">
                    <h2>Trip Details</h2>
                    <p>📍 <strong>Destination:</strong> %s / %s</p>
                    <p>📅 <strong>Date:</strong> %s</p>
                    <p>👥 <strong>Capacity:</strong> %s</p>
                    <p>🏨 <strong>Accommodation:</strong> %s</p>
                    <p>🚌 <strong>Transport:</strong> %s</p>
                  </div>
                  <div class="companyInfo">
                    <img src="https://i.ibb.co/wFMFk0z4/Group-68.png" alt="Lolopy Logo" />
                    <strong>Lolopy Travel</strong>
                    <p>Email: <a href="mailto:support@travelnow.com">support@travelnow.com</a></p>
                    <p>Phone: <a href="tel:+12345678901">+1 (234) 567-8901</a></p>
                    <p>Address: 123 Sunny Street, Uzhhorod, FL 33101, Ukraine</p>
                    <small>This message was sent to you because you booked a trip with Lolopy Travel.</small>
                  </div>
                </div>
              </body>
              </html>
              """,
          country, city, date, capacity, accommodation.toLowerCase(), transport.toLowerCase());

      helper.setText(htmlContent, true);

      mailSender.send(message);

    } catch (MessagingException e) {
      throw new RuntimeException("Cannot send an email", e);
    }
  }

  public void sendSignConfirmationEmail(String to) {
    MimeMessage message = mailSender.createMimeMessage();

    try {

      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setSubject("Підтвердження реєстрації");
      String htmlContent = String.format(
          """
                                      <!DOCTYPE html>
                      <html lang="en">
                      <head>
                        <meta charset="UTF-8" />
                        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                        <title>Successful Registration</title>
                        <style>
                body {
                  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                  margin: 0;
                  padding: 0;
                  background-color: #fff7f0;
                  color: #333;
                }

                .emailWrapper {
                  max-width: 640px;
                  margin: 50px auto;
                  background-color: #ffffff;
                  border-radius: 12px;
                  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.07);
                  overflow: hidden;
                  border: 1px solid #ddd;
                }

                .titleContainer {
                  background: linear-gradient(90deg, #0097a0, #b87333);
                  color: #ffffff;
                  padding: 35px 30px;
                  text-align: center;
                }

                .titleContainer h1 {
                  margin: 0;
                  font-size: 26px;
                  font-weight: 600;
                  letter-spacing: 0.5px;
                }

                .mainMessage {
                  padding: 30px 40px;
                  text-align: center;
                  font-size: 17px;
                  line-height: 1.8;
                  color: #444;
                }

                .mainMessage p {
                  margin-bottom: 15px;
                }

                .mainMessage .highlight {
                  font-weight: bold;
                  color: #0097a0;
                }

                .companyInfo {
                  background-color: #f4f1eb;
                  padding: 25px 30px;
                  font-size: 14px;
                  color: #888;
                  text-align: center;
                  border-top: 1px solid #ddd;
                }

                .companyInfo img {
                  width: 22%%;
                  margin-bottom: 12px;
                }

                .companyInfo strong {
                  font-size: 15px;
                  color: #222;
                  text-transform: uppercase;
                  letter-spacing: 1px;
                  display: block;
                  margin-bottom: 5px;
                }

                .companyInfo p {
                  margin: 6px 0;
                }

                .companyInfo a {
                  color: #444;
                  text-decoration: none;
                }

                .companyInfo small {
                  display: block;
                  margin-top: 14px;
                  font-size: 12px;
                  color: #999;
                }

                @media (max-width: 600px) {
                  .emailWrapper {
                    margin: 20px;
                  }

                  .mainMessage {
                    padding: 25px 20px;
                  }

                  .titleContainer h1 {
                    font-size: 22px;
                  }
                }
              </style>
                      </head>
                      <body>
                        <div class="emailWrapper">
                          <div class="titleContainer">
                            <h1>Welcome to Lolopy Travel!</h1>
                          </div>

                          <div class="mainMessage">
                            <p>🎉 Your registration on <span class="highlight">Lolopy Travel</span> was successful!</p>
                            <p>We're thrilled to have you on board. You can now start exploring amazing destinations and plan unforgettable trips with ease.</p>
                            <p>If you have any questions or need help, don't hesitate to reach out.</p>
                          </div>

                          <div class="companyInfo">
                            <img src="https://i.ibb.co/wFMFk0z4/Group-68.png" alt="Lolopy Logo" />
                            <strong>Lolopy Travel</strong>
                            <p>Email: <a href="mailto:support@travelnow.com">support@travelnow.com</a></p>
                            <p>Phone: <a href="tel:+12345678901">+1 (234) 567-8901</a></p>
                            <p>Address: 123 Sunny Street, Uzhhorod, FL 33101, Ukraine</p>
                            <small>You received this message because you registered with Lolopy Travel.</small>
                          </div>
                        </div>
                      </body>
                      </html>

                                      """);
      helper.setText(htmlContent, true);
      mailSender.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
