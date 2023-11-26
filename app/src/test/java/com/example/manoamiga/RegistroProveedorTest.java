package com.example.manoamiga;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import com.example.manoamiga.Registro_Proveedor;
@RunWith(RobolectricTestRunner.class)
public class RegistroProveedorTest {

    @Test
    public void testIsEmailValid() {
        Registro_Proveedor registroProveedor = new Registro_Proveedor();
        assertTrue(registroProveedor.isEmailValid("correo@dominio.com"));
        assertFalse(registroProveedor.isEmailValid("correoinvalido"));
    }

    @Test
    public void testIsPasswordValid() {
        Registro_Proveedor registroProveedor = new Registro_Proveedor();
        assertTrue(registroProveedor.isPasswordValid("clave123"));
        assertFalse(registroProveedor.isPasswordValid("abc"));
    }

    @Test
    public void testIsPhoneValid() {
        Registro_Proveedor registroProveedor = new Registro_Proveedor();
        assertTrue(registroProveedor.isPhoneValid("3012345678"));
        assertFalse(registroProveedor.isPhoneValid("123"));
    }
}
