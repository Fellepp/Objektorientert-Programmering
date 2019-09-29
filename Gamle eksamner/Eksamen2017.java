Del1

a)
public class Group {
	private int guestCount;
	private final Seating seating;
	
	public Group (int guestCount) {
		if (guestCount < 0) throw new IllegalArgumentException("You can't have negative guest count");
		this.guestCount = guestCount;
	}
	
	public int getGuestCount() {
		return guestCount
	}
	
	public void getSeated(Seating seating) {
		this.seating = seating;
	}
	
	public int changeGuestCount(int guestCount) {
		if (seating.getTable().getCapacity < guestCount) throw new IllegalArgumentException("The table is not big enough");
		this.guestCount = guestCount;
	}
}

public abstract class Table {
	private final int CAPACITY;
	private final int NUM;
	private static int tableCount = 1;
	
	public Table(int capacity) {
		if (capacity < 0) throw new IllegalArgumentException("You can't have negative capacity");
		this.CAPACITY = capacity;
		NUM = tableCount++;
	}
	
	protected int getCapacity() {
		return CAPACITY;
	}
	
	public int getNum() {
		return NUM;
	}
}

public class Seating {
	private final Table table;
	private final Group group;
	
	public Seating(Table table, Group group) {
		if (table.getCapacity < group.getGuestCount) throw new IllegalArgumentException("The table is not big enough for this group");
		this.table = table;
		this.group = group;
		group.getSeated(this);
	}
	
	public Table getTable() {
		return table;
	}
	
	public Group getGroup() {
		return group;
	}
}

Del2

a)
public class Diner implements {
	private Collection<Table> tables = new ArrayList<>();
	private Collection<Seating> seatings = new ArrayList<>();
	
	public void addTable(Table table) {
		if (!tables.contains(table)){
			tables.add(table);
			fireCapacityChanged(this);
		}
	}
	
	public void removeTable(Table table) {
		if (isOccupied(table)) throw new IllegalArgumentException("Can not remove an occupied table");
		tables.remove(table);
		fireCapacityChanged(this);
	}
	
	public boolean isOccupied(Table table) {
		return seatings.stream().anyMatch(s -> s.getTable() == table);
	}
	
	public int getCapacity(boolean includeOccupied) {
		return includeOccupied ? tables.stream().mapToInt(Table::getCapacity).sum() : tables.stream().filter(t -> !isOccupied(t)).mapToInt(Table::getCapacity).sum();
	}
	
	/*public void mergeTables(Table table1, Table table2, int lostCapaciy) {
		oldCap = table1.getCapacity() + table2.getCapacity();
		checkNotOccupied(table1);
		checkNotOccupied(table2);
		removeTable(table1); //KAN IKKE FJERNE FØR VI VET AT ALT ER GOOD!!!
		removeTable(table2);
		addTable(new Table(oldCap - lostCapacity));
	}*/
	
	public void mergeTables(Table table1, Table table2, int lostCapacity) {
		CompositeTable table = new CompositeTable(table1, table2, lostCapacity)
		removeTable(table1);
		removetable(table2);
		addTable(table);
	}
	
	private void checkNotOccupied(Table table){
		if (isOccupied(table)){
			throw new IllegalArgumentException("The table cannot be occupied");
		}
	}
	
	/*public void splitTable(Table table, int capacity1, int capacity2) {
		checkNotOccupied(table);
		removeTable(table);
		addTable(new Table(capacity1));
		addTable(new Table(capacity2));
	}*/
	
	public void splitTable(CompositeTable table){
		table1 = table.getTable1();
		table2 = table.getTable2();
		removeTable(table
		addTable(table1);
		addTable(table2);
		
	}
	
	public boolean hasCapacity(Table table, int capacity) {
		return table.getCapacity >= capacity && (!isOccupied(table));
	}
	
	public Collection<Table> findAvailableTables(int capacity) {
		Collection<Table> possibleTables = new ArrayList<>();
		for (Table table : tables) {
			if (hasCapacity(table, capacity)) possibleTables.add(table);
		}
		return ((ArrayList<Table>) possibleTables).sort((a, b) -> a.getCapacity() - b.getCapacity()).collect(Collectors.toList());
	}
	
	public Seating createSeating(Group group){
		Collection<Table> tablePossibilities = findAvailableTables(group.getGuestCount);
		return tablePossibilities.iterator().hasNext() ? tablePossibilites.iterator().next() : null;
	}
	
	public boolean addSeating(Group group) {
		Seating seating = createSeating(group);
		if (seating == null) return false;
		else {
			seatings.add(seating);
			fireCapacityChanged(this);
		}
		return true;	
	}
	
	public void removeSeating(int tableNum) {
		for (Seating seating : seatings){
			if (seating.getTable().getNum() == tableNum){
				seatings.remove(seating);
				fireCapacityChanged(this);
				return;
			}
		}
	}
	
	private Collection<CapacityListener> listeners = new ArrayList<>();
	
	public void addListener(CapacityListener listener){
		if (!listeners.contains(listener)) listeners.add(listener);
	}
	
	public void removeListener(CapacityListener lsitener){
		listeners.remove(listener);
	}
	
	public void fireCapacityChanged(Diner diner){
		for (CapacityListener listener : listeners){
			listener.capacityChanged(diner);
		}
	}
	
	public Collection<Table> getTables(){
		return tables;
	}
}

Del3

Kan la SimpleTable og Composite Table arve fra Table. Table kan enten være interface eller abstract superklasse (må ha getCapacity)

public class SimpleTable extends Table {
		private final int CAPACITY;
	private final int NUM;
	private static int tableCount = 1;
	
	public Table(int capacity) {
		if (capacity < 0) throw new IllegalArgumentException("You can't have negative capacity");
		this.CAPACITY = capacity;
		NUM = tableCount++;
	}
	
	protected int getCapacity() {
		return CAPACITY;
	}
	
	public int getNum() {
		return NUM;
	}
}

public class CompositeTable extends Table {
	private Table table1, table2;
	
	public CompositeTable(Table table1, Table table2, int lostCapacity) {
		super(table1.getCapacity() + table2.getCapacity() - lostCapacity)
		this.table1 = table1;
		this.table2 = table2;
	}
	
	public Table getTable1(){
		return table1;
	}
	
	public Table table2(){
		return table2;
	}
}

Del4

a) diner må være observerbar og må kunne ha metoder for å legge til og fjerne lyttere. Lyttere implementeret et interface
med metoder de må ha får å kunne følge med

public Interface CapacityListener{
	public void capacityChanged(Diner diner)
}

public class GuestManager implements CapacityListener{
	private Diner diner;
	private List<Group> groups = new ArrayList<>();
	
	
	public GuestManadger(Diner diner){
		this.Diner = Diner;
	}
	
	public void groupArrived(Group group){
		for (Table table : diner.findAvailableTables(group.getCapacity)){
			if (table == null) continue;
			diner.addSeating(group);
		}
		groups.add(group);
	}
	
	public void groupDeparted(int tableNum){
		diner.remove(tableNum);
	}
	
	@Override
	public void capacityChanged(Diner diner){
		for (Group group : groups){
			for (Table table : diner.findAvailableTables(group.getCapacity))
				if (table == null) continue;
				diner.addSeating(group);
				diner.remove(group);
			}
		}
	}
}

Del5

a) Kinda fordi en metode og kan b ruke lambda, men nei, det kan implementeres av mange der lytting ikke er hovedfunksjon, men sekundærmetode.
b) DONE
c) 
public class DinerTest implements junit.framework.TestCase{
	@Override
	public void setup(){
		Diner = new Diner();
		for (int i = 0; i < 10; i++){
			diner.addTable(i + 2);
		}
	}
	
	@Test
	publci void testOccupied(){
		Group group = new Group(2);
		diner.addSeating(group);
		
		testTable = diner.getTables().iterator().next();
		
		assertTrue(isOccupied(testTable));
		
		diner.removeSeating(testTable.getNum())
		
		Group group2 = new Group(10);
		diner.addSeating(group2);
		
		assertFalse(isOccupied(testTable));
		
	}
}




		