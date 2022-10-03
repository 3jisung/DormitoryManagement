package tableClass;

import java.io.Serializable;

//입사선발자 테이블
public class SelectedStudent  implements Serializable{
	private static final long serialVersionUID = 9L;
	private String sign_up_number;				//신청번호
	private String room_code;						//호실코드 101, 102
	private String bed_code;							//침대번호 A,B
	private int mng_cost;								//관리비
	private int food_cost;								//식비
	private int total_cost;								//합계
	private String pay_status;							//납부상태 o, x
	private String register_status;					//등록여부 o, x
	private String submissionTuberculosis;		//결핵진단서 제출여부 o, x
	private String dormitoryCode;					// 생활관분류코드 1 ,2
	
	public SelectedStudent() {}
	
	public SelectedStudent(String s, String r, String b, int m, int f, int t, String p, String rs, String tuber, String dormitoryCode)
	{
		sign_up_number = s;
		room_code = r;
		bed_code = b;
		mng_cost = m;
		food_cost = f;
		total_cost = t;
		pay_status = p;
		register_status = rs;
		submissionTuberculosis = tuber;
		this.dormitoryCode = dormitoryCode;
	}
	
	public String getSign_up_number() {return sign_up_number;}
	public String getRoom_code() {return room_code;}
	public String getBed_code() {return bed_code;}
	public int getMng_cost() {return mng_cost;}
	public int getFood_cost() {return food_cost;}
	public int getTotal_cost() {return total_cost;}
	public String getPay_status() {return pay_status;}
	public String getRegister_status() {return register_status;}
	public String getSubmissionTuberculosis() {return submissionTuberculosis;}
	public String getDormitoryCode() {return dormitoryCode;}
	
	public void setSign_up_number(String s) {sign_up_number = s;}
	public void setRoom_code(String r) {room_code = r;}
	public void setBed_code(String b) {bed_code = b;}
	public void setMng_cost(int m) {mng_cost = m;}
	public void setFood_cost(int f) {food_cost = f;}
	public void setTotal_cost(int t) {total_cost = t;}
	public void setPay_status(String p) {pay_status = p;}
	public void setRegister_status(String rs) {register_status = rs;}
	public void setSubmissionTuberculosis(String tuber) {submissionTuberculosis = tuber;}
	public void setDormitoryCode(String dormitoryCode) {this.dormitoryCode = dormitoryCode;} 
}
