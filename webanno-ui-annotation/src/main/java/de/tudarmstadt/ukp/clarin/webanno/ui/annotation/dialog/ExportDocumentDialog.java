/*
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.ui.annotation.dialog;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import de.tudarmstadt.ukp.clarin.webanno.api.annotation.model.AnnotatorState;
import de.tudarmstadt.ukp.clarin.webanno.ui.annotation.AnnotationPage;

/**
 * A panel used for {@link AnnotationPage} {@code CurationPage} and {@code CorrectionPage}
 * consisting of a link to export annotated data
 */
public class ExportDocumentDialog
    extends ModalWindow
{
    private static final long serialVersionUID = 671214149298791793L;

    private IModel<AnnotatorState> state;
    
    public ExportDocumentDialog(String id, final IModel<AnnotatorState> aModel)
    {
        super(id);
        
        state = aModel;

        setCookieName("modal-1");
        setInitialWidth(550);
        setInitialHeight(450);
        setResizable(true);
        setWidthUnit("px");
        setHeightUnit("px");
        setCssClassName("w_blue w_flex");
        showUnloadConfirmation(false);
        setTitle(new StringResourceModel("export"));
    }
    
    @Override
    public void show(IPartialPageRequestHandler aTarget)
    {
        setContent(new ExportDocumentDialogContent(getContentId(), this, state));
        super.show(aTarget);
    }
}
