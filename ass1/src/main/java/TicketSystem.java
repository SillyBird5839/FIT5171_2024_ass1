import java.util.Scanner;
import java.util.regex.Pattern;

public class TicketSystem {
    Scanner in;

    public TicketSystem() {
        this.in = new Scanner(System.in);
    }

    // 创建一个子类专门用来测试
    public TicketSystem(Scanner scanner) {
        this.in = scanner;
    }
    // 展示已购买的票信息
    public void showTicket(Ticket ticket) {
        if (ticket != null && ticket.getFlight() != null) {
            System.out.println("You have bought a ticket for flight " + ticket.getFlight().getDepartFrom() + " - " + ticket.getFlight().getDepartTo() + "\n\nDetails:");
            System.out.println(ticket.toString());
        } else {
            System.out.println("Error: Ticket details are incomplete or ticket is null.");
        }
    }

    // 购买一张票
    public void buyTicket(int ticket_id) {
        try {
            Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);
            if (validTicket == null || validTicket.ticketStatus()) {
                System.out.println("This ticket does not exist or is already booked.");
                return;
            }

            Passenger passenger = inputPassengerDetails();
            if (passenger == null) {
                System.out.println("Ticket booking cancelled.");
                return; // 如果乘客信息验证失败，直接返回
            }

            System.out.println("Do you want to proceed with the purchase? Enter YES to proceed or NO to cancel:");
            String decision = in.nextLine().trim();
            if (!decision.equalsIgnoreCase("YES")) {
                System.out.println("Ticket booking cancelled.");
                return;
            }

            validTicket.setPassenger(passenger);
            validTicket.saleByAge(passenger.getAge()); //changes price of the ticket according to the age category of passenger
            validTicket.setTicketStatus(true);  // 标记票为已购买
            System.out.println("Ticket booked successfully!");
            updateAirplaneSeats(validTicket);
            showTicket(validTicket);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 更新飞机座位
    private void updateAirplaneSeats(Ticket ticket) {
        Airplane airplane = ticket.getFlight().getAirplane();
        if (ticket.getClassVip()) {
            airplane.setBusinessSitsNumber(airplane.getBusinessSitsNumber() - 1);
        } else {
            airplane.setEconomySitsNumber(airplane.getEconomySitsNumber() - 1);
        }
    }

    // 选择票
    public void chooseTicket(String cityto, String cityfrom) {
        try {
            Flight flight = FlightCollection.getFlightInfo(cityto, cityfrom);
            if (flight != null) {
                TicketCollection.getAllTickets(); // 应该筛选只显示相关票
                System.out.println("\nEnter ID of ticket you want to choose:");
                int ticket_id = Integer.parseInt(in.nextLine());
                buyTicket(ticket_id);
            } else {
                System.out.println("No direct flights available from " + cityto + " to " + cityto);
                handleNoDirectFlight(cityto, cityfrom);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // 处理无直飞情况
    private void handleNoDirectFlight(String cityto, String cityfrom) {
        boolean keepSearching = true;
        int attempts = 0;

        while (keepSearching && attempts < 3) {  // Allow up to 3 attempts to find a connection
            Flight departToFlight = FlightCollection.getFlightInfo(cityto);
            if (departToFlight != null) {
                String connectCity = departToFlight.getDepartFrom();
                Flight connectingFlight = FlightCollection.getFlightInfo(connectCity, cityfrom);
                if (connectingFlight != null) {
                    System.out.println("A connecting flight is available via " + connectCity + ". Would you like to book? YES/NO:");
                    String decision = in.nextLine().trim();
                    if (decision.equalsIgnoreCase("YES")) {
                        buyTicket(departToFlight.getFlightID(), connectingFlight.getFlightID());
                        keepSearching = false;
                    } else {
                        System.out.println("Continue searching for another connection? YES/NO:");
                        decision = in.nextLine().trim();
                        keepSearching = decision.equalsIgnoreCase("YES");
                    }
                } else {
                    System.out.println("No connecting flights available via " + connectCity);
                    keepSearching = false;
                }
            } else {
                System.out.println("No flights available to the destination city " + cityto);
                keepSearching = false;
            }
            attempts++;
        }
        if (attempts >= 3) {
            System.out.println("Maximum attempts reached. No available flights found.");
        }
    }

    // 购买转机票
    public void buyTicket(int ticket_id_first, int ticket_id_second) {
        Ticket validTicketFirst = TicketCollection.getTicketInfo(ticket_id_first);
        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);

        if (validTicketFirst == null || validTicketSecond == null) {
            System.out.println("One or both tickets do not exist.");
            return;
        }

        // 购买第一张票
        buyTicket(ticket_id_first);
        // 购买第二张票
        buyTicket(ticket_id_second);
    }

    // 输入乘客详情并验证
    private Passenger inputPassengerDetails() {
        System.out.println("Enter your First Name: ");
        String firstName = in.nextLine();
        System.out.println("Enter your Second Name:");
        String secondName = in.nextLine();
        System.out.println("Enter your age:");
        int age = -1;
        try {
            age = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Please enter a valid number.");
            return null;
        }
        System.out.println("Enter your gender: ");
        String gender = in.nextLine();
        System.out.println("Enter your e-mail address:");
        String email = in.nextLine();
        System.out.println("Enter your phone number:");
        String phoneNumber = in.nextLine();
        System.out.println("Enter your passport number:");
        String passportNumber = in.nextLine();
        System.out.println("Enter your cardNumber number:");
        String cardNumber = in.nextLine();
        System.out.println("Enter your securityCode number:");
        String securityCode_string = in.nextLine();
        int securityCode = Integer.parseInt(securityCode_string);

        if (validateName(firstName) && validateName(secondName) && validateAge(age) &&
                validateEmail(email) && validatePhoneNumber(phoneNumber) && validatePassportNumber(passportNumber) && isValidCreditCard(cardNumber) && String.valueOf(securityCode).matches("\\d{3,4}")) {
            return new Passenger(firstName, secondName, age, gender, email, phoneNumber, passportNumber, cardNumber, securityCode);
        } else {
            System.out.println("Invalid input data. Please try again.");
            return null;
        }
    }

    private boolean validateName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean validateAge(int age) {
        return age > 0;
    }

    private boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^(04|05)\\d{8}$|^\\+614\\d{8}$");
    }


    private boolean validatePassportNumber(String passportNumber) {
        return passportNumber.length() <= 9;
    }

    private boolean isValidCreditCard(String number) {
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = number.charAt(i) - '0';
        }
        for (int i = digits.length - 2; i >= 0; i -= 2) {
            digits[i] *= 2;
            if (digits[i] > 9) digits[i] -= 9;
        }
        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }
        return sum % 10 == 0;
    }


}
