package tableClass;

import java.io.Serializable;
//가산점 테이블
public class AreaScore  implements Serializable{
	private static final long serialVersionUID = 4L;
	private String area;						//지역
	private Double score;					//가산점
	
	public AreaScore(String a, double s)
	{
		area = a;
		score = s;
	}
	
	public String getArea( ) { return area; }	
	public Double getScore() {return score;}
	
	public void setArea(String a) { area = a; }
	public void setScore(double s) {score = s;}
}
