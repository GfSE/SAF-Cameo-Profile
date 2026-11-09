package com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;

public interface State extends NamedElement {
    Region getContainer();
}
