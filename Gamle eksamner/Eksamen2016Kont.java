Del1


a) Hver boks er et klassediagram med Navn og attributter. Pilene betyr at man bare kan gå en vei (fra pilstart til pilspiss)
slik at Person vil ha attributer Course og Exam, men ikke motsatt. I tillegg vill Exam ha informasjon om et Course, men
et course vil ikke ha informasjon om en exam.
* betyr at man har en x til mange forhold, der en Person kan ha flere eksamner og flere klasser.
En eksamen kan kun tilhøre et kurs.

b) Innkapsling og modifikatorer, samt hvilke metoder som trengs og hvilke parametere skal konstruktørene ha inn. Trenger noen av de å
implementere noe?

c) Kan være problematisk å regne snitt. Kan også være problematisk med karakterer somm ikke finnes
Kan lage en klasse Grade som har en static konstruktør med de forskjellige karakterene som finnes, samt en getValue 
metode som gir et tall tilbale tilsvarende karakteren. Kunne også hatt Map<Integer, Character>

public Class Grade {
	private final Characeter grade;
	public static Grade A = new Grade("A"), B = new Grade("B"), C = new Grade("C"), 
						D = new Grade("D"), E = new Grade("E"), F = new Grade("F");
	private static final List<Grade> grades = new ArrayList<>(Arrays.asList({F, E, D, C, B, A}));
	
	private Grade(Character grade) {
		this.grade = grade;
	}
	
	public String toString(){
		return grade;
	}
	
	public int valueOf(Character grade) {
		for (int i = 0; i <grades.size(); i++){
			if (grade == grades.get(i).toString()) return i + 1;
		}
	}
}

Del2

public class Course implements Comparable<Course>{
	private final String code;
	private int credits;
	private String time;
	
	public Course(String code) {
		this.code = code;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public String getCode(){
		return code;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public int getYear() {
		String year = "";
		for (int i = 1; i < time.length(); i++) {
			year += time.charAt(i) + "";
		}
		return Integer.parseInt(year);
	}
	
	public String getSemester() {
		return time.charAt(0) + "";
	}
	
	public String getTime() {
		return time;
	}
	
	public String formatCheck(String time) {
		if (time.length() == 5) return time;
		else if (time.length() != 3) throw new IllegalArgumentException("Too many or too few characters. Should be V2018 or V18");
		String year = "";
		for (int i = 1; i < time.length(); i++) {
			year += time.charAt(i); + "";
		}
		return ((Integer.parseInt(year)) < 50) ? Character.toUpperCase(time.charAt(0)) + "20" + year : 
													Character.toUpperCase(time.charAt(0)) + "19" + year;
	}
	
	public void setTime(String time) {
		if (! (Character.toUpperCase(time.charAt(0)) == "S" || Character.toUpperCase(time.charAt(0)) == "F")) {
			throw new IllegalArgumentException("Wrong format. Should be in this format <semester><year>");
		}
		this.time = formatCheck(time);
	}
	
	@Override
	public int compareTo(Course c){
		//Antar at det menes S2016, V2016, S2017, V2017, S2018, V2018...
		if (this.getYear() != c.geYear()) return c.getYear() - this.getYear(); 
		return this.getSemester().compareTo(c.getSemester());
	}
}

public class Exam implements Comparable<Exame>{
	private Grade grade;
	private final Course course;
	
	public Exam(Grade grade, Course course) {
		if (grade == null)
		this.grade = grade;
		this.course = course;
	}
	
	public void setGrade(Grade grade) {
		if (grade == null) throw new IllegalArgumentException("Invalid grade");
		this.grade = grade;
	}
	
	public Grade getGrade() {
		return grade;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public boolean isPass() {
		if this.grade.toString().equals("F") return false;
		return true;
	}
	
	public int compareTo(Exam other) {
		return course.compareTo(other.getCourse());
	}
}

public class ExamComparaor implements Comparator<Exam> {
	@Override
	public int compare(Exam exam1, Exam exam2){
		return exam1.getGrade().toString() - exam2.getGrade().toString();
	}
}

Del3

public class Person {
	private final String name;
	Collection<Course> courses = new ArrayList<>(); //Inneholder flere courses av type Course. Bruker Collection fordi index trengs ikke
	Collection(Exam) exams = new ArrayList<>(); //samme som courses
	
	public Person (String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean addCourse(Course course) {
		if(!courses.contains(course){
			courses.add(course);
			return true;
		}
		return false;
	}
	
	public boolean hasCourse(String code) {
		for (Course course : courses){
			if (course.getCode.equals(code)) return true;
		}
		return false;
	}
	
	public Exam addExam(Course course, char grade) {
		if (courses.contains(course)) {
			for (Exam exam : exams) {
				if (exam.getCourse() == course && exam.isPass()) return null;
			for (Grade g : Grade.grades){
				if (g.toString().equals(grade)) {
					Exam exam = new Exam(course, g);
					break;
			}
			exams.add(exam);
			return exam;
		}
		return null;
	}
	
	public Exam getLastExam(String code) {
		return exams.stream().filter(x x-> g.getCourse().getCode() == code).sorted().collect(Collectors.toList()).get(0);
	}
	
	public boolean hasPassed(String code) {
		return exams.stream().filter(x -> x.getCourse().geCode().equals(code)).anyMatch(x -> x.isPass(x.getGrade()));
	}
	
	public double countCredits() {
		return exams.stream().filter(x == x.getLastExam((x.getCourse().getCode()).filter(x -> x.hasPassed(x.getCourse().getCode())).reduce((a,b) -> a + b).get();
	}
}

Collection<Exam> readExams(Reader input) throws IOException{
	Collection<Exam> exams = new ArrayList<>();
	Scanner sc = new Scanner(input);
	String time;
	while(sc.hasNextLine()){
		String[] line = sc.nextLine().split(" ");
		//SEMESTER
		if (line.length == 1){
			grades.clear();
			time = line[0];
			continue;
		}
		//COURSE		
		Course course = new Courste(line[0]);
		course.setCredits(Integer.parseInt(line[1]));
		course.setTime(time);
		
		for (int i = 2; i < line.length; i++){
			Exam exam = new Exam(line[i], course)
			if (!examx.contains(exam)) exams.add(exam);
		}	
	}
	sc.close();
	return exams;
}


Del 4

public class ExamRequirement {
	public final String course;
	public final int sinceYear;
	public final char minGrade;
	
	public ExamRequirement(String course, int sinceYear, char minGrade){
		this.course = course;
		this.sinceYear = sinceYear;
		this.minGrade = minGrade;
	}
	
	public ExamRequirement(String course, int siceYear) {
		this(course, sinceYear, "E");
	}	

	public final static ExamRequirement atLeastCInTdt4100 = new ExamRequirement("TDT4100", 0, "C");
}

d) Kan ha en klasse som arver fra ExamRequirements. acceptsCourse kan forandres til startsWith. Bør være protected


private boolean acceptsCourse(Course course){
	return acceptsCourseCode(course.getCode()) && course.getGear() >= sinceYear;
}

protected boolean acceptsCourseCode(String course){
	return course.equals(this.course)
}

Subklassen:
@Override
protected boolean acceptsCourseCode(String course){
	return course.startsWith(this.course);
}

public Interface IExamRequirement{
	public boolean accepts(Exam exam);
}
Nyttig for å deklarere metoder alle instanser av ExamRequirement må ha

f)
public static IExamRequirement atleastCinJava = (exam) -> "TDT4100".equals(exam.getCourse().getCode()) && exam.getrade() <= "C";


