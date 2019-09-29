Del

a)
label feltet bør være private final
konstruktøren bør være private

b)
Bruker toString() metoden som returnerer label

c)
public static Gender valueOf(String label){
	if label.equals(MALE.label) return MALE;
	else if label.equals(FEMALE.label) return FEMALE;
	return null;
}

d)
public class Person implements Iterable<Person>{
	private final String name;
	private Gender gender = null;
	private Person father, mother;
	private Collection<Person> children = new ArrayList<>();
	
	public Person(String name){
		this.name = name;
	}
	
	public void setGender(Gender gender){
		this.gender = gender
	}
	
	public String getName(){
		return name;
	}
	
	public Gender getGender(){
		return gender;
	}
	
	public Person getMother(){
		return mother;
	}
	
	public Person getFather(){
		return father;
	}	
	
	public void setParent(Person parent){
		if (parent.getGender().equals(Gender.MALE)){
			if (father == null) father = parent;
			else{
				father.removeChild(this);
				father = parent;
			}
		}else if (parent.getGender().equals(Gender.FEMALE)[
			if (mother = null) mother = parent;
			else{
				mother.removeChild(this);
				mother = parent;
			}
		}
	}
	
	public void removeChild(Person child){
		children.remove(child);
	}
e)
	public int getChildCount(){
		return children.size();
	}
	
	public boolean hasChild(Person child){
		if (children.contains(child)) return true;
		return false;
	}
	
	public Collection<Person> getChildren(Gender gender){
		if (gender == null) return children;
		return children.stream()
			.filter(x -> x.getGender.equals(gender)
			.collect(Collectors.toList());
	}
f)
Det betyr at man nå kan lage en iterator og går gjennom barna til en Person ved å la en annen klasse implementere
iterator<Person>. Kan nå bruke for (Person child: person)
	@Override
	public Iterable<Person> iterator{
		return children.iterator();
	}
g)
	public void addChild(Person child){
		if (!children.contains(child)){
			children.add(child);
			child.setParent(this);
		}
	}

h)
setGender(female) -> addChild (child)
assertTrue(child.getMother(father2);
//Tar plassen til moren. Kunne evnt hatt to foreldre av samme kjønn, eller kontroll over biologisk
//far/mor og current far/mor

child.addChild(father);
assertFalse(child.hasChild(father))
//Kan ikke ha sin egen foreldre som barn
}

Del2

a)

public class Family{
	private Collection<Person> family = new ArrayList<>();
	
	public void addMember(Person person){
		if (!family.contains(person)) family.add(person);
	}
	
	public Person findMember(String name){
		for (Person person : family){
			if person.getName.equals(name) return person;
		}
		return null;
	}
b)
	private static, hjelpemetode og brukes kun av metoder i sin egen klasse
c)
	private void printNames(Person person, PrintWriter pw){
		pw.print("\"" + person.getName() + "\"")
	}
	
	public void save(OutputStream out) throws IOException{
		PrinWriter pw = new PrintWriter(out);
		println("# all persons");
		for (Person person : family){
			pw.print(person.getGender());
			pw.print(" ");
			printNames(person, pw);
			pw.println();
		}
		pw.println();
		pw.println("# all mother/father-child relations");
		for (Person person : family){
			if (person.iterator().hasNext()){
				printName(person, pw);
				for (Person child : person){
					pw.print(" ");
					printName(child, pw);
				}
				pw.println();
			}
		}
		pw.flush();
	}
	
	public void load(InputStream in) throws IOException{
		Scanner scanner = new Scanner(in);
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			if (line.trim().length() == 0 || line.startsWith("#")) continue;
			List<String> tokens = tokenize(line);
			Gender gender = Gender.valueOf(tokens.get(0));
			if (gender != null){
				Person person = new Person(tokens.get(1));
				person.setGender(gender);
				addMember(person);
			}else{
				Person person = findMember(tokens.get(0));
				
				for (int i = 1; i < tokens.size(); i++){
					Person child = findMember(tokens.get(i));
					person.addChild(child);
				}
			}
			
		}
		scanner.close();
	}	
}

Del3

a)

	public class Sister implements Relation{
		
		@Override
		public Collection<Person> getRelativesOf(Person person){
			Collection<Person> sisters = new ArrayList<>();
			if (person.getFather() != null) {
				List<Person> fatherSide = person.getFather.stream().filter(x -> x.getGender() == Gender.FEMALE).collect(Collectors.toList());
				
				sisters.addAll(fatherSide);
			}
			
			if (person.getMoher() != null) {
				List<Person> motherSide = person.getMother.stream().filter(x -> x.getGender() == Gender.FEMALE).collect(Collectors.toList());
				
				for (Person sis : motherSide){
					if (!sisters.contains(sis)) sisters.add(sis);
				}
			}
			
			sisters.remove(person);
			return sisters;
		}
	}
}
	
	public class Parent implements Relation{
		
		@Override
		public Collection<Person> getRelativesOf(Person person){
			Collecton<Person> parents = new ArrayList<>();
			if (person.getFather != null) parents.add(person.getFather());
			if (person.getMother != null) parents.add(person.getMother());
			return parents;
		}
	}
}

b) Hva slags standardteknikk og hva karakteriserer den? Kan brukes til delegering. 
	
	public class Relation2 implements Relation{
		private final Relation rel1, rel2;
		
		public Relation2(Relation rel1, Relation rel2){
			this.rel1 = rel1;
			this.rel2 = rel2;
		}
		
		@Override
		public Collection<Person> getRelativesOf(Person person){
			Collection<Person> result1 = rel1.getRelativesOf(person);
			Collection<Person> result2 = new ArrayList<>();
			for (Person person1 : result1){
				result2.addAll(rel2.getRelativesOf(person1));
			}
			return result2;
		}
	}
} 
c) Oldeforeldre er det samme som foreldrene til foreldrene til foreldrene. Kan relaliseres slik;

Relation parent = new Parent();
relation grandParent = new Relation2(parent, parent);
relation grandGrandParent = new Relation2(grandParent, parent);

Del4

a) 
public class Sibling implements Relation{
	private final Gender gender;
	
	protected Sibling(Gender gender){
		this.gender = gender,
	}
	
	@Override
	protected Collection<Person> getRelativesOf(Person person){
		Collection<Person> siblings = new ArrayList<>();
		if (person.getFather() != null || gender == null) {
			List<Person> fatherSide;
			fatherSide = ((gender != null) ? person.getFather().stream().filter(x -> x.getGender() == gender).collect(Collectors.toList()) : person.getFather().getChildren());
			siblings.addAll(fatherSide);
		}
		
		if (person.getMoher() != null || gender == null) {
			List<Person> motherSide;
			motherSide = ((gender != null) ? person.getMother().stream().filter(x -> x.getGender() == gender).collect(Collectors.toList()) : person.getMother().getChildren());
			
			for (Person sib : motherSide){
				if (!siblings.contains(sib)) siblings.add(sib);
			}
		}		
		siblings.remove(person);
		return siblings;
	}
}

public class Sister extends Sibling{
	Private Person person;
	
	public Sister(Person person){
		super(Gender.FEMALE);
		this.person = person;
	}
	
	@Override
	public Collection<Person> getRelativesOf(Person person){
		super.getRelativesOf(person);
	}
}

public class Brother extends Sibling{
	private Person person;
	
	public Brother(Person person){
		super(Gender.MALE);
		this.person = person;
	}
	
	@Override
	public Collection<Person> getRelativesOf(Person person){
		super.getRelativesOf(person);
	}
}

b) Ingen skal være det. Alle tre er nyttige (SKULLE LATT SIBLING FINNE SØSKEN SOM DEFAULT)
c) Funkjonelle grensesnitt er grensesnitt med EN metode. I tillegg skal for klassen som implementerer den, så skal 
grensesnittet være dens primære metode. Relation oppfyller dette og er derfor et funksjonelt grensesnitt
d)

Relation daughter = (p) -> p.getChildren(Gender.FEMALE);




