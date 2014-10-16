/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devnexus.ting.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.jboss.aerogear.unifiedpush.JavaSender;
import org.jboss.aerogear.unifiedpush.message.UnifiedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devnexus.ting.core.model.User;
import com.devnexus.ting.core.model.UserCalendar;
import com.devnexus.ting.core.service.BusinessService;
import com.devnexus.ting.core.service.CalendarServices;
import com.devnexus.ting.web.JaxbJacksonObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * This class will service a users requests for their personalized calendar.
 *
 * @author summers
 */
@Controller
public class CalendarController {

    private static final Gson GSON = new GsonBuilder().create();

	@Value("#{environment.TING_PUSH_APP_ID}")
    private String PUSH_APP_ID;

	@Value("#{environment.TING_PUSH_MASTER_SECRET}")
    private String PUSH_APP_SECRET;

    static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    @Autowired
    private JaxbJacksonObjectMapper mapper;

    @Autowired
    JavaSender javaSender;

    @Autowired
    CalendarServices calendarService;

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value={"/usercalendar"}, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserCalendar>>  calendar() throws JsonProcessingException {
        return calendar(businessService.getCurrentEvent().getEventKey());
    }

    @RequestMapping(value={"/{eventKey}/usercalendar"}, method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserCalendar>>  calendar(@PathVariable("eventKey") String eventKey) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  String) {

            headers.add("WWW-Authenticate", "Google realm=\"http://www.devnexus.org\"");

            return new ResponseEntity<List<UserCalendar>>(new ArrayList<UserCalendar>(), headers, HttpStatus.UNAUTHORIZED);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserCalendar> calendar = calendarService.getUserCalendar(user, eventKey);
        return new ResponseEntity<>(calendar, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/{eventKey}/usercalendar/{id}", method={RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ResponseEntity<UserCalendar>  updateCalendar(@PathVariable("eventKey") String eventKey, @PathVariable("id") String id, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  String) {
            headers.add("WWW-Authenticate", "Google realm=\"http://www.devnexus.org\"");
            return new ResponseEntity<>(new UserCalendar(), headers, HttpStatus.UNAUTHORIZED);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        UserCalendar calendar = null;
        try {
            calendar = GSON.fromJson(request.getReader(), UserCalendar.class);

            calendar = calendarService.updateEntry(calendar.getId(), user, calendar);

             UnifiedMessage unifiedMessage = new UnifiedMessage.Builder()
                .pushApplicationId(PUSH_APP_ID)
                .masterSecret(PUSH_APP_SECRET)
                .aliases(Arrays.asList(user.getEmail()))
                .attribute("org.devnexus.sync.UserCalendar", "true")
                .build();

            javaSender.send(unifiedMessage);

            return new ResponseEntity<>(calendar, headers, HttpStatus.OK);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value="/usercalendar/{id}", method={RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ResponseEntity<UserCalendar>  updateCalendar(@PathVariable("id") String id, HttpServletRequest request) {
        return updateCalendar(businessService.getCurrentEvent().getEventKey(), id, request);

    }

}