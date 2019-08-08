package in.sarma.thumbola.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class ThumbolaService {

	private List<Integer> numList = new ArrayList<>();
	private List<Integer> numList2 = new ArrayList<>();
	private int num;
	private int prenum;
	private StringBuilder table;
	private Random random;
	private final int maxNumber = 90;

	ThumbolaService() {
		for(int i = 1; i <= maxNumber; i++ ) {
			numList2.add(i);
		}

		random = new Random();
	}

//	@Scheduled(fixedDelay = 3000)
	public void timer() {
		generatenum(true);
	}

	public StringBuilder generatenum(boolean flag) {

		if(flag) {
			if(numList2.size() > 0 ) {
				num = numList2.get(random.nextInt(numList2.size()));
				table = recreatetable(num);
				prenum = num;
			}
		} else {
			num = 0;
			table = recreatetable(num);
		}
		return table;
	}

	public void clearBoard() {
		table = new StringBuilder("");
	}


	public StringBuilder recreatetable(Integer addkey) {
		System.out.println(addkey);

		StringBuilder boardhtml = new StringBuilder("<table style=\"font-size: 11pt; -moz-border-radius: 5px; -webkit-border-radius: 5px;border-radius: 6px;border: 1px solid blue;box-shadow: 10px 10px 5px #888888;\">");

		if(addkey != 0) {
			numList.add(addkey);
			numList2.remove(addkey);
		}

		for(int i = 1; i <= maxNumber; i++)
		{
			if(i==1 || i==11 || i==21 || i==31 || i==41 || i==51 || i==61 || i==71 || i==81 )
				boardhtml.append("<tr>");

			if(numList.indexOf(i) > -1)
			{
				if(i<=10)
					boardhtml.append("<td bgcolor='red' id='td" + i + "' style='text-align: right; padding: 6px; border: 1px solid blue;'><font " +"color='white'>" + i + "</font></td>");
				else
					boardhtml.append("<td bgcolor='red' id='td" + i + "' style='padding: 6px; border: 1px solid blue;'><font color='white'>" + i + "</font></td>");
			}
			else
			{
				if(i<=10)
					boardhtml.append("<td id='td" + i + "' style='text-align: right; padding: 6px; border: 1px solid blue;'>" + i + "</td>");
				else
					boardhtml.append("<td id='td" + i + "' style='text-align: right; padding: 6px; border: 1px solid blue;'>" + i + "</td>");
			}
			if(i==10 || i==20 || i==30 || i==40 || i==50 || i==60 ||  i==70 || i==80 )
				boardhtml.append("</tr>");
		}
		boardhtml.append("</table>");

		return boardhtml;
	}

	public StringBuilder getTable() {
		return table;
	}
}
