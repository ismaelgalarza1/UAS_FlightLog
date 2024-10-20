public class Hours extends Operator {
    //
    private double hours; //

    // Constructor
    public Hours(int id, String name, int numOfFlights, double hours) {
        super(id, name, numOfFlights, hours);
        this.hours = hours;
    }

    // Getter
    public Double getHours() {
        return hours;
    }

    // Setter
    public void setHours(double hours) {
        this.hours = hours;
    }

    // toString method
    @Override
    public String toString() {
        return "This Operator " + getName() + " has a total of " + hours + " flight hours.";
    }
}
