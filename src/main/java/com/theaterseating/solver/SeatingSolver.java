package com.theaterseating.solver;

import java.util.List;
import java.util.Scanner;
import com.theaterseating.data.Layout;
import com.theaterseating.data.Request;

/**
 * The driver class for reading the input layout, requests and computing
 * the appropriate distribution of seats.
 */
public class SeatingSolver {
    public static void main(String[] args) {
        // Logic to read input and solve the theater seating problem
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide the layout - ");
        StringBuilder layout = new StringBuilder();
        String input;
        while ((input = scanner.nextLine()) != null) {
            if (input.length() == 0) {
                break;
            }
            layout.append(input + "\n");
        }

        System.out.println("Please provide the requests - ");
        StringBuilder ticketRequests = new StringBuilder();
        while ((input = scanner.nextLine()) != null) {
            if (input.length() == 0) {
                break;
            }
            ticketRequests.append(input + "\n");
        }
        scanner.close();

        SeatingSearch service = new SeatingSearch();

        try{
            Layout theaterLayout = service.getTheaterLayout(layout.toString());
            List<Request> requests = service.getTicketRequests(ticketRequests.toString());
            service.processTicketRequests(theaterLayout, requests);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}