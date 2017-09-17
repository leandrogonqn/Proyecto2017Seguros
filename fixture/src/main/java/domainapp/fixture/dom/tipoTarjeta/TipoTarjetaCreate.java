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

package domainapp.fixture.dom.tipoTarjeta;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.tipoTarjeta.TipoTarjeta;
import domainapp.dom.tipoTarjeta.TipoTarjetaMenu;



public class TipoTarjetaCreate extends FixtureScript {

    //region > name (input)
    private String name;
    /**
     * Name of the object (required)
     */
    public String getName() {
        return name;
    }

    public TipoTarjetaCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion


    //region > simpleObject (output)
    private TipoTarjeta simpleObject;

    /**
     * The created simple object (output).
     * @return
     */
    public TipoTarjeta getSimpleObject() {
        return simpleObject;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.simpleObject = wrap(simpleObjectMenu).crear(name);

        // also make available to UI
        ec.addResult(this, simpleObject);
    }

    @javax.inject.Inject
    private TipoTarjetaMenu simpleObjectMenu;

}