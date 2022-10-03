package tableClass;
//결핵진단서 테이블
public class Tuberculosis_certificate {
	private static final long serialVersionUID = 3L;
	
	private int year;						//연도
	private int semester;				//학기
	private String std_number;		//학번
	private String sbm_date;			//제출일시
	private String ctf_date;				//전단 일시
	private String f_path;				//파일경로	
	private String f_name;				//파일명
	
	public Tuberculosis_certificate(int y, int s, String sn, String sd, String cd, String fp, String fn)
	{
		year = y;
		semester = s;
		std_number = sn;
		sbm_date = sd;
		ctf_date = cd;
		f_path = fp;
		f_name = fn;
	}
	
	public int getYear() {return year;}
	public int getSemester() {return semester;}
	public String getStd_number() {return std_number;}
	public String getSbm_date() { return sbm_date;}
	public String getCtf_date() {return ctf_date;}
	public String getF_path() {return f_path;}
	public String getF_name() {return f_name;}
	
	public void setYear(int y) {year = y;}
	public void setSemester(int s) {semester = s;}
	public void setStd_number(String sn) {std_number = sn;}
	public void setSbm_date(String sd) {sbm_date = sd;}
	public void setCtf_date(String cd) {ctf_date = cd;}
	public void setF_path(String fp) {f_path = fp;}
	public void setF_name(String fn) {f_name = fn;}
}
