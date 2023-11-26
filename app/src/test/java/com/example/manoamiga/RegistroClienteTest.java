package com.example.manoamiga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import com.example.manoamiga.Registro_Cliente;
@RunWith(RobolectricTestRunner.class)
public class RegistroClienteTest {

    @Test
    public void testIsEmailValid() {
        Registro_Cliente registroCliente = new Registro_Cliente();
        assertTrue(registroCliente.isEmailValid("correo@dominio.com"));
        assertFalse(registroCliente.isEmailValid("correoinvalido"));
    }

    @Test
    public void testIsPasswordValid() {
        Registro_Cliente registroCliente = new Registro_Cliente();
        assertTrue(registroCliente.isPasswordValid("clave123"));
        assertFalse(registroCliente.isPasswordValid("abc"));
    }

    @Test
    public void testIsPhoneValid() {
        Registro_Cliente registroCliente = new Registro_Cliente();
        assertTrue(registroCliente.isPhoneValid("3012345678"));
        assertFalse(registroCliente.isPhoneValid("123"));
    }
}

