package com.andersenlab.dao;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.andersenlab.App;
import com.andersenlab.model.Hotel;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
public class HotelDaoTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	HotelDao hotelDao;

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

		assertTrue(3 == findAll.getNumberOfElements());
	}

	@Test
	public void testFindByHotelName() {
		Hotel hotelTest = new Hotel();
		hotelTest.setHotelName("Grand Hotel 1");
		entityManager.persist(hotelTest);
		entityManager.flush();

		Hotel findByHotelName = hotelDao.findByHotelName("Grand Hotel 1");

		assertTrue(hotelTest.equals(findByHotelName));
	}

	@Test
	public void testSave() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Grand Hotel");
		entityManager.persist(hotel);
		entityManager.flush();

		Hotel findByHotelNameResult = hotelDao.findByHotelName(hotel.getHotelName());

		assertTrue(findByHotelNameResult.getHotelName().equals(hotel.getHotelName()));
	}

	@Test
	public void testDeleteById() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Grand Hotel");
		entityManager.persist(hotel);
		entityManager.flush();

		Optional<Hotel> findByHotelNameResult = hotelDao.findById(hotel.getId());
		entityManager.remove(findByHotelNameResult.get());
		entityManager.flush();

		assertTrue(null == hotelDao.findByHotelName(hotel.getHotelName()));
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

		assertTrue(null == hotelDao.findByHotelName(hotel.getHotelName()));

	}
}

