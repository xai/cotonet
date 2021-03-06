/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alcemir R. Santos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package br.com.riselabs.cotonet.model.dao.validators;

import br.com.riselabs.cotonet.model.beans.ConflictBasedNetwork;
import br.com.riselabs.cotonet.model.beans.DeveloperNode;
import br.com.riselabs.cotonet.model.dao.MergeScenarioDAO;
import br.com.riselabs.cotonet.model.exceptions.InvalidCotonetBeanException;

/**
 * @author Alcemir R. Santos
 *
 */
public class ConflictBasedNetworkValidator implements
		Validator<ConflictBasedNetwork> {

	/**
	 * Checks whether the network is well formed or not.
	 * 
	 * @return {@code flag} - with value <code>true</code> when the network is
	 *         well formed, <code>false</code> otherwise.
	 * @throws InvalidCotonetBeanException 
	 */
	public boolean validate(ConflictBasedNetwork obj) throws InvalidCotonetBeanException {
		if (obj.getNodes() == null 
				|| obj.getEdges() == null 
				|| obj.getMergeScenarioID() == null)
			throw new InvalidCotonetBeanException(
					ConflictBasedNetwork.class, 
					"Either #getNodes(), #getEdges(), or #getMergeScenarioID() returned <null>.",
					new NullPointerException());
		if (obj.getNodes().isEmpty() && !obj.getEdges().isEmpty())
			throw new InvalidCotonetBeanException(
					ConflictBasedNetwork.class, 
					"Either #getNodes() or #getEdges() were empty while the other has contents.",
					new IllegalArgumentException());
		if (!obj.getNodes().isEmpty() && obj.getEdges().isEmpty())
			throw new InvalidCotonetBeanException(
					ConflictBasedNetwork.class, 
					"Either #getNodes() or #getEdges() were empty while the other has contents.",
					new IllegalArgumentException());
		
		// checking nodes
		for (DeveloperNode dnode : obj.getNodes()) {
			// data must not be null
			if ( dnode.getEmail() == null)
				throw new InvalidCotonetBeanException(
						ConflictBasedNetwork.class, 
						"There is a developer node with an `Email' <null>.",
						new NullPointerException());
			// data must not be empty
			if (dnode.getEmail().equals(""))
				throw new InvalidCotonetBeanException(
						ConflictBasedNetwork.class, 
						"There is a developer node with an `Email' invalid.",
						new IllegalArgumentException());
			// developers must have different IDs and Emails
			for (DeveloperNode anode : obj.getNodes()) {
				if (dnode.equals(anode))
					continue;
				else if ( dnode.getEmail().equals(anode.getEmail()))
					throw new InvalidCotonetBeanException(
							ConflictBasedNetwork.class, 
							"There is two developer nodes with same  `Email'.",
							new NullPointerException());
			}
		}
//		// checking edges
//		for (DeveloperEdge dedge : obj.getEdges()) {
//			if (dedge.getDevA().getID() == null || dedge.getDevB().getID() == null)
//				throw new InvalidCotonetBeanException(
//						ConflictBasedNetwork.class, 
//						"There is a developer edge with either the `LeftID' or the `RightID' <null>.",
//						new NullPointerException());
//			if (!isThereSuchDeveloper(dedge.getLeft())
//					|| !isThereSuchDeveloper(dedge.getRight()))
//				throw new InvalidCotonetBeanException(
//						ConflictBasedNetwork.class, 
//						"Threre is a developer edge with either an `LeftID' or an `RightID' invalid.",
//						new NullPointerException());
//		}
		// checking merge scenario
		if(new MergeScenarioDAO().get(obj.getScenario())==null){
			throw new InvalidCotonetBeanException(
					ConflictBasedNetwork.class, 
					"There is no such a merge scenario ("+obj.getScenario().getID()
					+") in the database.",
					new NullPointerException());
		}
			
		return true;
	}

//	/**
//	 * checks the existence of developer with such ID.
//	 * 
//	 * @param id
//	 * @return
//	 */
//	private boolean isThereSuchDeveloper(Integer id) {
//		DeveloperNodeDAO ddao = (DeveloperNodeDAO) DAOFactory.getDAO(CotonetBean.NODE);
//		DeveloperNode n = null;
//		try {
//			n = ddao.get(new DeveloperNode(id, null, null, null));
//		} catch (InvalidCotonetBeanException e) {
//			Logger.logStackTrace(e);
//		}		
//		if (n.getID() == id)
//			return true;
//		return false;
//	}
}
