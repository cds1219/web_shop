package web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import web.service.MemberService;
import web.util.Calculator;
import web.util.ShopException;
import web.vo.MemberVO;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
	MemberService service;
	
	@Override
	public void init() throws ServletException {
		service=new MemberService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myService(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myService(request, response);
	}
	
	protected void myService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		String sign=request.getParameter("sign");
		
		if("memberInsert".equals("sign")) {
			String name=request.getParameter("name");
			String id=request.getParameter("id");
			String pw=request.getParameter("pw");
			MemberVO vo=new MemberVO(id,pw,name);
			System.out.println(vo);
			
			try {
				service.insertMember(vo);
				out.append(name+"님 가입되셨습니다.");
			} catch (ShopException e) {
				out.append(e.getMessage());
			}
		}else if(sign.equals("login")) {
			String id=request.getParameter("id");
			String pw=request.getParameter("pw");
			MemberVO vo=new MemberVO(id,pw);
			System.out.println(vo);
			
			try {
				String name=service.selectMember(vo);
				if(name!=null) {
					HttpSession session= request.getSession();
					session.setAttribute("id", id);
					System.out.println(session.getId());
					out.append(name);
				}else {
					out.append("login 실패");
				}
			} catch (ShopException e) {
				out.append(e.getMessage());
			}
		}else if(sign.equals("subject")){//체크박스
			String [] subject_value=request.getParameterValues("subject_value[]");
			
			for(String value:subject_value) {
				System.out.println("알고있는 과목:"+value);
			}
		}else if(sign.equals("calc")){//환율계산
			String won=request.getParameter("won");
			String operator=request.getParameter("operator");
			
			String result=Calculator.calculate(Float.parseFloat(won),operator);
			out.append(result);
			
		}else if(sign.equals("memberDelete")) {//회원탈퇴
			HttpSession session= request.getSession();//오브젝트타입
			String id=(String)session.getAttribute("id");
			System.out.println(session.getId()+" "+id);
			try {
				service.deleteMember(id);
				out.append("회원탈퇴 되셨습니다.");
			} catch (ShopException e) {
				out.append(e.getMessage());
			}
			
		}else if(sign.equals("logout")) {//로그아웃 -> 세션무효화
			HttpSession session=request.getSession();
			session.invalidate();
			System.out.println("logout ok");
			out.append("logout ok");
			
		}else if(sign.equals("loginForm")) {//loginForm
			String id=request.getParameter("id");
			String pw=request.getParameter("pw");
			MemberVO vo=new MemberVO(id,pw);
			System.out.println(vo);
			
			try {
				String name=service.selectMember(vo);
				if(name!=null) {//ok
					HttpSession session= request.getSession();
					session.setAttribute("id", id);
					
					RequestDispatcher disp=request.getRequestDispatcher("login_ok.jsp");
					request.setAttribute("name", name);
					disp.forward(request, response);
				}else {//fail
					RequestDispatcher disp=request.getRequestDispatcher("login_fail.jsp");
					disp.forward(request, response);
				}
			} catch (ShopException e) {
				RequestDispatcher disp=request.getRequestDispatcher("login_fail.jsp");
				disp.forward(request, response);
			}
		}
		
	}
	
}
