package com.sydney.vacbook;

import com.sydney.vacbook.controller.BookingController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.bind.annotation.RequestMapping;
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BookingControllerTest {

    protected MockMvc mockMvc;


    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BookingController()).build();
    }

    //current not working
    @Test
    public void testPage() throws Exception{
        this.mockMvc.perform(get("/vacbook/booking/user/48")).andExpect(status().isOk());
    }
}
