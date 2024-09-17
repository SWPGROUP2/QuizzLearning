
<!-- Sidebar  -->


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<nav id="sidebar">
    <div class="sidebar-header">
        <h3>QPS04</h3>
    </div>

    <ul class="list-unstyled components">
        <p>Quiz Practicing System</p>
        <li class="active">
            <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Subjects</a>
            <ul class="collapse list-unstyled" id="homeSubmenu">
                <li>
                    <a href="subject-list">Subject list</a>
                </li>
                <li>
                    <a href="my-registration">My Subject</a>
                </li>

            </ul>
        </li>

        <li>
            <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Quizzes</a>
            <ul class="collapse list-unstyled" id="pageSubmenu">
                <li>
                    <a href="QuizHistoryServlet">Quiz History</a>
                </li>
                <li>
                    <a href="quiz-list">Quiz List</a>
                </li>

            </ul>
        </li>

    </ul>
</nav>


