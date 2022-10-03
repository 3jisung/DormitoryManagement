package tableClass;

import java.io.Serializable;
//학생테이블
public class Student implements Serializable
{
	private static final long serialVersionUID = 11L;
	private String studentId;					//학번
	private String name;							//이름, not null
	private String gender;						//성별
	private String departmentName;		//학과명
	private int grade;								//학년
	private String studentAddress;			//학생주소
	private String studentPhoneNumber;//학생전화번호

	public Student(String studentId, String name)
	{
		this.studentId = studentId;
		this.name = name;
		gender = null; departmentName = null;
		grade = 0; studentAddress = null; studentPhoneNumber = null;
	}

	public Student (Student some)
	{
		studentId = some.getStudentId();	
		name = some.getName();	
		gender = some.getGender(); departmentName = some.getDepartmentName(); grade = some.getGrade();
		studentAddress = some.getStudentAddress(); studentPhoneNumber = some.getStudentPhoneNumber();		
	}
	
	public String getStudentId() {return studentId;}
	public String getName() {return name;}
	public String getGender() {return gender;}
	public String getDepartmentName() {return departmentName;}
	public int getGrade() {return grade;}
	public String getStudentAddress() {return studentAddress;}
	public String getStudentPhoneNumber() {return studentPhoneNumber;}

	public void setStudentId(String studentId) {this.studentId = studentId;}
	public void setName(String name) {this.name = name;}
	public void setGender(String gender) {this.gender = gender;}
	public void serDepartmentName(String departmentName) {this.departmentName = departmentName;}
	public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
	public void setGrade(int grade) {this.grade = grade;}
	public void setStudentAddress(String studentAddress) {this.studentAddress = studentAddress;}
	public void setStudentPhoneNumber(String studentPhoneNumber) {this.studentPhoneNumber = studentPhoneNumber;}
}