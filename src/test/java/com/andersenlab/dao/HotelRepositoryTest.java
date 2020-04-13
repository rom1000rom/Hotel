package com.andersenlab.dao;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.andersenlab.exceptions.HotelServiceException;
import dao.AbstractDaoTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.andersenlab.model.Hotel;


public class HotelRepositoryTest extends AbstractDaoTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	HotelRepository hotelDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Hotel hotelTest = new Hotel();
		// hotelTest.setHotelName("Grand Hotel");
		// entityManager.persist(hotelTest);
		// entityManager.flush();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllPageable() {
		Hotel hotelTest = new Hotel();
		hotelTest.setHotelName("Grand Hotel 1");
		entityManager.persist(hotelTest);
		hotelTest = new Hotel();
		hotelTest.setHotelName("Grand Hotel 2");
		entityManager.persist(hotelTest);
		hotelTest = new Hotel();
		hotelTest.setHotelName("Grand Hotel 3");
		entityManager.persist(hotelTest);
		entityManager.flush();

		Pageable firstPageWithFiveElements = PageRequest.of(0, 10);
		Page<Hotel> findAll = hotelDao.findAll(firstPageWithFiveElements);

		assertEquals(5, findAll.getNumberOfElements());
	}

	@Test
	public void testFindByHotelName() {
		Hotel hotelTest = new Hotel();
		hotelTest.setHotelName("Grand Hotel 1");
		entityManager.persist(hotelTest);
		entityManager.flush();

		Hotel findByHotelName = hotelDao.findByHotelName("Grand Hotel 1");

		assertEquals(hotelTest, findByHotelName);
	}

	@Test
	public void testSave() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Grand Hotel");
		entityManager.persist(hotel);
		entityManager.flush();

		Hotel findByHotelNameResult = hotelDao.findByHotelName(hotel.getHotelName());

		assertEquals(findByHotelNameResult.getHotelName(), hotel.getHotelName());
	}

	@Test
	public void testDeleteById() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Grand Hotel");
		entityManager.persist(hotel);
		entityManager.flush();

		Optional<Hotel> findByHotelNameResult = hotelDao.findById(hotel.getId());
		entityManager.remove(findByHotelNameResult.orElseThrow(() ->
				new HotelServiceException("Such a hotel does not exist")));
		entityManager.flush();

		assertNull(hotelDao.findByHotelName(hotel.getHotelName()));
	}

	@Test
	public void testDelete() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Grand Hotel");
		entityManager.persist(hotel);
		entityManager.flush();

		Hotel findByHotelNameResult = hotelDao.findByHotelName(hotel.getHotelName());
		entityManager.remove(findByHotelNameResult);
		entityManager.flush();

		assertNull( hotelDao.findByHotelName(hotel.getHotelName()));
	}
}
