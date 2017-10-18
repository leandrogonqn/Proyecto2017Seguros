/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.common.collect.Lists;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.TipoDocumento;
import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.fixture.dom.cliente.ClienteCreate;
import domainapp.fixture.dom.cliente.ClienteTearDown;

import org.apache.isis.applib.annotation.Parseable;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;


public class RecreateCliente extends FixtureScript {

	
    public final List<String> NAMES = Collections.unmodifiableList(Arrays.asList(
            "Juan"));
    
    public final List<String> apellidos = Collections.unmodifiableList(Arrays.asList(
            "Perez"));
    
    public final List<String> documentos = Collections.unmodifiableList(Arrays.asList(
            "31313131"));
    
    public final List<String> direcciones = Collections.unmodifiableList(Arrays.asList(
    		"Salta 123"));
    
    public final List<String> telefonos = Collections.unmodifiableList(Arrays.asList(
    		"447-1351"));    
    
    public final List<String> emails = Collections.unmodifiableList(Arrays.asList(
    		"juan.perez@gmail.com"));
    

    public RecreateCliente() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > number (optional input)
    private Integer number;

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    public Integer getNumber() {
        return number;
    }

    public RecreateCliente setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    //region > simpleObjects (output)
    private final List<Cliente> simpleObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Programmatic
    public List<Cliente> getSimpleObjects() {
        return simpleObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 1);

        // validate
        if(number < 0 || number > NAMES.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES.size()));
        }

        //
        // execute
        //
        ec.executeChild(this, new ClienteTearDown());
        
        GregorianCalendar fechaNacimiento = new GregorianCalendar(1985,6,8);
        
        for (int i = 0; i < number; i++) {
            final ClienteCreate fs = new ClienteCreate();
            fs.setName(NAMES.get(i));
            fs.setApellido(apellidos.get(i));
            fs.setDni(Integer.parseInt(documentos.get(i)));
            fs.setLocalidad(localidadesRepository.listarActivos().get(0));
            fs.setSexo(Sexo.Masculino);
            fs.setDireccion(direcciones.get(i));
            fs.setTelefono(telefonos.get(i));
            fs.setEmail(emails.get(i));
            fs.setFechaNacimiento(fechaNacimiento.getTime());
            fs.setTipoDocumento(TipoDocumento.DNI);
            ec.executeChild(this, fs.getName(), fs);
            simpleObjects.add(fs.getSimpleObject());
        }
    }
    
    @javax.inject.Inject
    LocalidadRepository localidadesRepository;
}
