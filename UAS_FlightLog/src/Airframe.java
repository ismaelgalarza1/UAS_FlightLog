public class Airframe extends Operator {
    // Attribute
    private String airframeName;

    // Constructor
    public Airframe(int id, String name, int numOfFlights, double hours, String airframeName) {
        super(id, name, numOfFlights, hours); // Calls the constructor of Operator
        this.airframeName = airframeName; // Initialize airframeName in the subclass
    }

    // Getter
    public String getAirframeName() {
        return airframeName;
    }

    // Setter
    public void setAirframeName(String airframeName) {
        this.airframeName = airframeName;
    }

    // toString method (Overrides the Operator's toString method)
    @Override
    public String toString() {
        return "This operator " + getName() + " has total flight hours of " + getHours() + " on " + airframeName + ".";
    }
}
