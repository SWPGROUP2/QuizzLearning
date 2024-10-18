        
package controllers;

import dal.QuestionDAO;
import models.Question;


public class QuestionService {
    private QuestionDAO questionDAO = new QuestionDAO();

    public Question getQuestionsWithCount(int subjectId, int currentPage, int pageSize) {
        return questionDAO.getQuestionsWithCount(subjectId, currentPage, pageSize); 
    }
}


