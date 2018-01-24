# TheaterSeating

To execute the program from terminal:

mvn clean install
mvn exec:java -Dexec.mainClass="com.theaterseating.solver.SeatingSolver"

Please provide the layout when prompted, and the hit return key. You will then be prompted to enter the requests.
Once you hit return key again, the program will compute the seating distribution and print it to standard output.


Example run:
Please enter the layout -
6 6
3 5 5 3
4 6 6 4
2 8 8 2
6 6

Please enter the requests -
Smith 2
Jones 5
Davis 6
Wilson 100
Johnson 3
Williams 4
Brown 8
Miller 12

Seats Distribution -
Smith Row 1 Section 1
Jones Row 2 Section 2
Davis Row 1 Section 2
Wilson Sorry, we can't handle your party.
Johnson Row 2 Section 1
Williams Row 1 Section 1
Brown Row 4 Section 2
Miller Call to split party.
