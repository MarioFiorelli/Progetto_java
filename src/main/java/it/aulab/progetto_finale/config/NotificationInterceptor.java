package it.aulab.progetto_finale.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.aulab.progetto_finale.repositories.ArticleRepository;
import it.aulab.progetto_finale.repositories.CareerRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor  implements HandlerInterceptor{
    @Autowired
    CareerRequestRepository careerRequestRepository;

    @Autowired
    private ArticleRepository articleRepository;
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && request.isUserInRole("ROLE_ADMIN")) {
            int careerCount=careerRequestRepository.findByIsCheckedFalse().size();
            modelAndView.addObject("careerRequest", careerCount);
    }

    if(modelAndView != null && request.isUserInRole("ROLE_REVISOR")) {
        int revisedCount=articleRepository.findByIsAcceptedNull().size();
        modelAndView.addObject("articlesToBeRevised", revisedCount);
}
}
}