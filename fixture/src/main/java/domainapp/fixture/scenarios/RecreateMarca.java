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
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.marca.Marca;
import domainapp.fixture.dom.marca.MarcaCreate;
import domainapp.fixture.dom.marca.MarcaTearDown;

public class RecreateMarca extends FixtureScript {

    public final List<String> NAMES = Collections.unmodifiableList(Arrays.asList(
            "Chevrolet", "Ford", "Fiat", "Alfa Romeo", "Mercedes Benz", "BMW", "Audi", "Peugeout", "Renault", "Chery"));

    public RecreateMarca() {
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

    public RecreateMarca setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    //region > simpleObjects (output)
    private final List<Marca> simpleObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    @Programmatic
    public List<Marca> getSimpleObjects() {
        return simpleObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 10);

        // validate
        if(number < 0 || number > NAMES.size()) {
            throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES.size()));
        }

        //
        // execute
        //
        ec.executeChild(this, new MarcaTearDown());

        for (int i = 0; i < number; i++) {
            final MarcaCreate fs = new MarcaCreate().setName(NAMES.get(i));
            ec.executeChild(this, fs.getName(), fs);
            simpleObjects.add(fs.getSimpleObject());
        }
    }
}
