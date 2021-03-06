package com.theaterseating.data;

/**
 * Stores all the information related to a particular request.
 */
public class Request {
    private String personName;
    private int nonTickets;
    private boolean isOk;
    private int rowNumber;
    private int sectionNumber;

    public String getName() {
        return personName;
    }

    public void setName(String name) {
        this.personName = name;
    }

    public int getNoOfTickets() {
        return nonTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.nonTickets = noOfTickets;
    }

    public boolean isCompleted() {
        return isOk;
    }

    public void setCompleted(boolean isCompleted) {
        this.isOk = isCompleted;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getStatus(){
        String status;
        if(isOk){
            status = personName + " " + "Row " + rowNumber + " " + "Section " + sectionNumber;
        } else {
            if(rowNumber == -1 && sectionNumber == -1){
                status = personName + " " + "Call to split party.";
            } else {
                status = personName + " " + "Sorry, we can't handle your party.";
            }
        }
        return status;
    }
}
