
package controllers;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/data/customer")
public class CustomerDataDownloadController {

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {
		final String myString = "Below these lines you can find all the data we have at Acme\r\n";

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=my_data_as_member.txt");
		final ServletOutputStream out = response.getOutputStream();
		out.println(myString);
		out.flush();
		out.close();
	}

}
