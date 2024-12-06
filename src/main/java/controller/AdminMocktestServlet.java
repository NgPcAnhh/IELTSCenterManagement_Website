package controller;

import dao.AdminMocktestDao;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.stream.Collectors;

@WebServlet("/MocktestManagement")
public class AdminMocktestServlet extends HttpServlet {
    private AdminMocktestDao mockTestDAO = new AdminMocktestDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String action = request.getParameter("action");

        try (PrintWriter out = response.getWriter()) {
            JSONObject result = new JSONObject();

            if ("updateMockTest".equals(action)) {
                // Nhận dữ liệu từ yêu cầu POST và xử lý cập nhật điểm và feedback
                JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
                String id = json.optString("id", null);
                String idTest = json.optString("idTest", null);
                String time = json.optString("time", null);
                double reading = json.optDouble("reading", 0);
                double listening = json.optDouble("listening", 0);
                double writing = json.optDouble("writing", 0);
                double speaking = json.optDouble("speaking", 0);
                double overall = json.optDouble("overall", 0);
                String feedbackR = json.optString("feedback_r", "");
                String feedbackL = json.optString("feedback_l", "");
                String feedbackW = json.optString("feedback_w", "");
                String feedbackS = json.optString("feedback_s", "");

                // Cập nhật điểm
                boolean scoreUpdateSuccess = true;
                scoreUpdateSuccess &= mockTestDAO.updateMockTestScore(id, idTest, "reading", reading);
                scoreUpdateSuccess &= mockTestDAO.updateMockTestScore(id, idTest, "listening", listening);
                scoreUpdateSuccess &= mockTestDAO.updateMockTestScore(id, idTest, "writing", writing);
                scoreUpdateSuccess &= mockTestDAO.updateMockTestScore(id, idTest, "speaking", speaking);
                scoreUpdateSuccess &= mockTestDAO.updateMockTestScore(id, idTest, "overall", overall);

                // Cập nhật feedback
                boolean feedbackUpdateSuccess = mockTestDAO.updateMockTestFeedback(id, idTest, feedbackR, feedbackL, feedbackW, feedbackS);

                // Gửi phản hồi cho client về kết quả cập nhật
                result.put("success", scoreUpdateSuccess && feedbackUpdateSuccess);
                out.write(result.toString());

            }

            else if ("addMocktest".equals(action)) {
                // Nhận dữ liệu từ yêu cầu POST để thêm mock test mới
                JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
                String id = json.getString("id");
                String idTest = json.getString("idTest");
                Date time = Date.valueOf(json.getString("time"));
                double reading = json.optDouble("reading", 0);
                double listening = json.optDouble("listening", 0);
                double writing = json.optDouble("writing", 0);
                double speaking = json.optDouble("speaking", 0);
                double overall = json.optDouble("overall", 0);
                String feedbackR = json.optString("feedback_r", "");
                String feedbackL = json.optString("feedback_l", "");
                String feedbackW = json.optString("feedback_w", "");
                String feedbackS = json.optString("feedback_s", "");

                // Thực hiện thêm mocktest trong DAO
                boolean success = mockTestDAO.addMockTest(id, idTest, time, reading, listening, writing, speaking, overall, feedbackR, feedbackL, feedbackW, feedbackS);
                result.put("success", success);
                out.write(result.toString());
            }
            else {
                // Xử lý hành động POST không hợp lệ
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                result.put("error", "Invalid action for POST request: " + action);
                out.write(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", e.getMessage()).toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String action = request.getParameter("action");

        try (PrintWriter out = response.getWriter()) {
            if ("getAllMockTests".equals(action)) {
                JSONArray mockTests = mockTestDAO.getAllMockTests();
                out.write(mockTests.toString());
                response.setStatus(HttpServletResponse.SC_OK);
            } else if ("getMocktest".equals(action)) {
                String id = request.getParameter("id");
                String idTest = request.getParameter("idTest");

                if (id != null && idTest != null) {
                    JSONObject result = mockTestDAO.getMockTestByIdAndIdTest(id, idTest);
                    out.write(result.toString());
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write(new JSONObject().put("error", "Invalid parameters for retrieving mock test.").toString());
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write(new JSONObject().put("error", "Invalid action for GET request. Action: " + action).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(new JSONObject().put("error", e.getMessage()).toString());
        }
    }
}
