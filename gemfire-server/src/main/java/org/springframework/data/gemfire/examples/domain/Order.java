/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.gemfire.examples.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.gemfire.mapping.Region;

import org.springframework.util.Assert;

@Region
public class Order extends AbstractPersistentEntity {

	private static final long serialVersionUID = -3779061453639083037L;

	private Long customerId;
	private Address billingAddress;
	private Address shippingAddress;
	private Set<LineItem> lineItems = new HashSet<LineItem>();

	/**
	 * Creates a new {@link Order} for the given {@link Customer}.
	 *
	 * @param customer must not be {@literal null}.
	 */
	public Order(Long id, Long customerId, Address billingAddress) {
		super(id);
		Assert.notNull(customerId);
		Assert.notNull(billingAddress);

		this.customerId = customerId;
		this.billingAddress = billingAddress;
		this.shippingAddress = billingAddress;
	}

	/**
	 * Adds the given {@link LineItem} to the {@link Order}.
	 *
	 * @param lineItem
	 */
	public void add(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}

	/**
	 * Returns the id of the {@link Customer} who placed the {@link Order}.
	 *
	 * @return
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * Returns the billing {@link Address} for this order.
	 *
	 * @return
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Returns the shipping {@link Address} for this order;
	 *
	 * @return
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Returns all {@link LineItem}s currently belonging to the {@link Order}.
	 *
	 * @return
	 */
	public Set<LineItem> getLineItems() {
		return Collections.unmodifiableSet(lineItems);
	}

	/**
	 * Returns the total of the {@link Order}.
	 *
	 * @return
	 */
	public BigDecimal getTotal() {

		BigDecimal total = BigDecimal.ZERO;

		for (LineItem item : lineItems) {
			total = total.add(item.getTotal());
		}

		return total;
	}
}
