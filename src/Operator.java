public class Operator {
    private int id;
    private String name;
    private int numOfFlights;
    private Double hours;
    private String airframeName;

    // Constructor
    public Operator(int id, String name, int numOfFlights, double hours) {
        this.id = id;
        this.name = name;
        this.numOfFlights = numOfFlights;
        this.hours = hours;
        this.airframeName = "";
    }

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfFlights() {
        return numOfFlights;
    }

    public void setNumOfFlights(int numOfFlights) {
        this.numOfFlights = numOfFlights;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getAirframeName() {
        return airframeName;
    }

    public void setAirframeName(String airframeName) {
        this.airframeName = airframeName;
    }

    @Override
    public String toString() {
        return "Operator ID: " + id + ", Name: " + name + ", Flights: " + numOfFlights +
                ", Total Hours: " + (hours != null ? hours : "Not set") + ", Airframe: " + airframeName;
    }
}
