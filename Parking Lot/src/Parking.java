import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ParkingLot{
	int size, index;
	List<String> carNos, carColours;
	List<Integer> freeLots;
	
	public ParkingLot(int size) {
		carNos = new ArrayList<>();
		carColours = new ArrayList<>();
		this.size = size;
		freeLots = new ArrayList<>();
		for( int i = 0; i < size; i++)
			freeLots.add(i);
		index = 0; 
	}
	
	public void parkCar( String carNo, String colour ) {
		if( carNos.size() < size || ! freeLots.isEmpty()) {
			carNos.add(freeLots.get(index), carNo );
			carColours.add(freeLots.get(index), colour );
			System.out.println("Allocated Slot number: "+ (freeLots.get(index) + 1));
			freeLots.remove(index);
			
		}
		else
			System.out.println("Sorry, parking lot is full");
		
	}
	
	public void leaveCar( int index ) {
		if( index > 0 && index < carNos.size() ) {
			freeLots.add( index - 1 );
			carNos.remove(index - 1);
			System.out.println("Slot Number "+index+" is free");
		}
	}
	
	public void display() {
		System.out.println("Slot.No\t\tRegistration No.\t\tColour");
		for( int i = 0; i < carNos.size(); i++ ) {
			if(carNos.get(i) != null) {
				System.out.println(i+1+"\t\t"+carNos.get(i)+"\t\t\t"+carColours.get(i));
			}
		}
	}
	
	public void getCarsBasedOnColours(String str, String colour) {
		for( int i = carColours.indexOf(colour); i <= carColours.lastIndexOf(colour); i++ ) {
			if(str.contains("registration_numbers")) {
				if(carColours.get(i).equals(colour))
					if(i == carColours.lastIndexOf( colour ) )
						System.out.println(carNos.get(i));
					else
						System.out.print(carNos.get(i)+", ");
				}
			else if(str.contains("slot_numbers")) {
				if(carColours.get(i).equals(colour))
					if(i == carColours.lastIndexOf( colour ) )
						System.out.println(i + 1);
					else
						System.out.print(i + 1+", ");
			}
		}
	}
	
	public void getSlotNumberBasedOnCarNumber(String carNo) {
		if(carNos.contains(carNo))
			System.out.println(carNos.indexOf(carNo)+1);
		else
			System.out.println("Not found");
	}
}

public class Parking {
	public static void main(String args[]) {
		BufferedReader reader;
		ParkingLot parkingLot = null;
		List<String> splitString;
		try {
			reader = new BufferedReader(new FileReader(
					System.getProperty("user.dir") +"/parking_lot.txt"));
			String line = reader.readLine();
			while (line != null) {
				splitString = Arrays.asList(line.split(" "));
				switch(splitString.get(0)) {
					case "create_parking_lot":
						parkingLot = new ParkingLot(Integer.parseInt(splitString.get(1)));
						break;
					case "park":
						parkingLot.parkCar(splitString.get(1), splitString.get(2));
						break;
					case "leave":
						parkingLot.leaveCar(Integer.parseInt(splitString.get(1)));
						break;
					case "registration_numbers_for_cars_with_colour":
						parkingLot.getCarsBasedOnColours(splitString.get(0),splitString.get(1));
						break;
					case "slot_numbers_for_cars_with_colour":
						parkingLot.getCarsBasedOnColours(splitString.get(0),splitString.get(1));
						break;
					case "slot_number_for_registration_number":
						parkingLot.getSlotNumberBasedOnCarNumber(splitString.get(1));
						break;
					case "exit":
						return;
					default:
						parkingLot.display();
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
