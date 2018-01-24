package com.theaterseating.solver;


import com.theaterseating.data.Section;
import com.theaterseating.data.Layout;
import com.theaterseating.data.Request;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Implementation of the search algorithm
 **/
public class SeatingSearch {


    public Layout getTheaterLayout(String rawLayout) throws NumberFormatException {

        Layout layout = new Layout();
        Section section;
        List<Section> sectionsList = new ArrayList<Section>();
        int totalCapacity = 0, value;
        String[] rows = rawLayout.split(System.getProperty("line.separator"));
        String[] sections;

        for (int i = 0; i < rows.length; i++) {

            sections = rows[i].split(" ");

            for (int j = 0; j < sections.length; j++) {

                try {

                    value = Integer.valueOf(sections[j]);

                } catch (NumberFormatException nfe) {

                    throw new NumberFormatException("'" + sections[j] + "'" + " is invalid section capacity. Please correct it.");

                }

                totalCapacity = totalCapacity + value;

                section = new Section();
                section.setRowNumber(i + 1);
                section.setSectionNumber(j + 1);
                section.setCapacity(value);
                section.setAvailableSeats(value);

                sectionsList.add(section);

            }

        }

        layout.setTotalCapacity(totalCapacity);
        layout.setAvailableSeats(totalCapacity);
        layout.setSections(sectionsList);

        return layout;

    }


    public List<Request> getTicketRequests(String ticketRequests) throws NumberFormatException {

        List<Request> requestsList = new ArrayList<Request>();
        Request request;

        String[] requests = ticketRequests.split(System.getProperty("line.separator"));

        for (String r : requests) {

            String[] rData = r.split(" ");

            request = new Request();

            request.setName(rData[0]);

            try {

                request.setNoOfTickets(Integer.valueOf(rData[1]));

            } catch (NumberFormatException nfe) {

                throw new NumberFormatException("'" + rData[1] + "'" + " is invalid ticket request. Please correct it.");
            }
            request.setCompleted(false);

            requestsList.add(request);

        }

        return requestsList;

    }


    private int findComplementRequest(List<Request> requests, int complementSeats, int currentRequestIndex) {

        int requestNo = -1;

        for (int i = currentRequestIndex + 1; i < requests.size(); i++) {

            Request request = requests.get(i);

            if (!request.isCompleted() && request.getNoOfTickets() == complementSeats) {

                requestNo = i;
                break;

            }

        }

        return requestNo;
    }


    /*
     * Find section by it's available seats
     *
     */
    private int findSectionByAvailableSeats(List<Section> sections, int availableSeats) {

        int i = 0;
        Section section = new Section();
        section.setAvailableSeats(availableSeats);

        Collections.sort(sections);

        Comparator<Section> byAvailableSeats = new Comparator<Section>() {


            public int compare(Section o1, Section o2) {

                return o1.getAvailableSeats() - o2.getAvailableSeats();

            }
        };

        int sectionNo = Collections.binarySearch(sections, section, byAvailableSeats);

        /*
         * sectionNo < 0 means could not find section
         * sectionNo = 0 means found section and it's very first one
         * sectionNo > 0 means found section but have to check for duplicate sections
         *
         */

        if (sectionNo > 0) {

            for (i = sectionNo - 1; i >= 0; i--) {

                Section s = sections.get(i);

                if (s.getAvailableSeats() != availableSeats) break;

            }

            sectionNo = i + 1;

        }

        return sectionNo;
    }

    public void processTicketRequests(Layout layout, List<Request> requests) {

        for (int i = 0; i < requests.size(); i++) {

            Request request = requests.get(i);
            if (request.isCompleted()) continue;

            /*
             * -2 is an indicator when we can't handle the party.
             */
            if (request.getNoOfTickets() > layout.getAvailableSeats()) {

                request.setRowNumber(-2);
                request.setSectionNumber(-2);
                continue;

            }

            List<Section> sections = layout.getSections();

            for (int j = 0; j < sections.size(); j++) {

                Section section = sections.get(j);

                if (request.getNoOfTickets() == section.getAvailableSeats()) {

                    request.setRowNumber(section.getRowNumber());
                    request.setSectionNumber(section.getSectionNumber());
                    section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                    layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                    request.setCompleted(true);
                    break;

                } else if (request.getNoOfTickets() < section.getAvailableSeats()) {

                    int requestNo = findComplementRequest(requests, section.getAvailableSeats() - request.getNoOfTickets(), i);

                    if (requestNo != -1) {

                        request.setRowNumber(section.getRowNumber());
                        request.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                        request.setCompleted(true);

                        Request complementRequest = requests.get(requestNo);

                        complementRequest.setRowNumber(section.getRowNumber());
                        complementRequest.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - complementRequest.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - complementRequest.getNoOfTickets());
                        complementRequest.setCompleted(true);

                        break;

                    } else {

                        int sectionNo = findSectionByAvailableSeats(sections, request.getNoOfTickets());

                        if (sectionNo >= 0) {

                            Section perferctSection = sections.get(sectionNo);

                            request.setRowNumber(perferctSection.getRowNumber());
                            request.setSectionNumber(perferctSection.getSectionNumber());
                            perferctSection.setAvailableSeats(perferctSection.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;

                        } else {

                            request.setRowNumber(section.getRowNumber());
                            request.setSectionNumber(section.getSectionNumber());
                            section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;

                        }

                    }

                }

            }

            /*
             * -1 is an indicator when we need to split the party.
             */
            if (!request.isCompleted()) {

                request.setRowNumber(-1);
                request.setSectionNumber(-1);

            }

        }

        System.out.println("Seats Distribution.\n");

        for (Request request : requests) {

            System.out.println(request.getStatus());

        }

    }
}
