package com.theaterseating.data;

import java.util.List;

/**
 * Class to store the overall status of the layout in the theater
 */
public class Layout {
        private int totalSeats;
        private int usableSeats;
        private List<Section> sections;

        public int getTotalCapacity() {
            return totalSeats;
        }

        public void setTotalCapacity(int totalCapacity) {
            this.totalSeats = totalCapacity;
        }

        public int getAvailableSeats() {
            return usableSeats;
        }

        public void setAvailableSeats(int availableSeats) {
            this.usableSeats = availableSeats;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }
}