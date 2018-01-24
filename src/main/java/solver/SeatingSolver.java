package solver;

import java.util.List;
import java.util.Scanner;
import data.TheaterLayout;
import data.TheaterRequest;

public class SeatingSolver {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String line;
        StringBuilder layout = new StringBuilder();
        StringBuilder ticketRequests = new StringBuilder();
        boolean isLayoutFinished = false;

        // Logic to read input and solve the theater seating problem
        Scanner input = new Scanner(System.in);

        while((line = input.nextLine()) != null && !line.equals("end")){

            if(line.length() == 0){

                isLayoutFinished = true;
                continue;

            }

            if(!isLayoutFinished){

                layout.append(line + "\n");

            }else{

                ticketRequests.append(line + "\n");

            }

        }

        input.close();

        SeatingSearch service = new SeatingSearch();

        try{

            TheaterLayout theaterLayout = service.getTheaterLayout(layout.toString());

            List<TheaterRequest> requests = service.getTicketRequests(ticketRequests.toString());

            service.processTicketRequests(theaterLayout, requests);

        }catch(NumberFormatException nfe){

            System.out.println(nfe.getMessage());

        }catch(Exception e){

            e.printStackTrace();

        }
    }
}