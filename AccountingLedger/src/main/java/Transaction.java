import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class Transaction {
    //declare variables for constructor
    String date;
    String time;
    String description;
    String vendor;
    double amount;
	Date sqlDate;
	Time sqlTime;

	public Transaction() {}

    //Constructor to create new Transaction (instances of)
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

	public Transaction(Date sqlDate, Time sqlTime, String description, String vendor, double amount) {
		this.sqlDate = sqlDate;
		this.sqlTime = sqlTime;
		this.description = description;
		this.vendor = vendor;
		this.amount = amount;
	}

    //region Getters and Setters
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

	public Time getSqlTime() {
		return sqlTime;
	}

	public void setSqlTime(Time sqlTime) {
		this.sqlTime = sqlTime;
	}

	public Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	//Used for sorting
    public String getDateTime() {
        return date + "-" + time;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    //endregion

	public void print(){
		String amountColor = "";
		if(this.amount > 0) {
			System.out.println(Utils.GREEN + "----------DEPOSIT----------" + Utils.RESET);
			amountColor = Utils.GREEN;
		} else if(this.amount <= 0) {
			System.out.println(Utils.RED + "----------PAYMENT----------" + Utils.RESET);
			amountColor = Utils.RED;
		}
		System.out.println("Date: " + this.sqlDate);
		System.out.println("Time: " + this.sqlTime);
		System.out.println("Description: " + this.description);
		System.out.println("Vendor: " + this.vendor);
		System.out.println("Amount: " + amountColor + "$" + this.amount + Utils.RESET);
	}
}
