import { check } from 'k6';

export function generateUniqueUser(vuId, iter) {
  const timestamp = Date.now();
  return {
    name: `QA_User_${vuId}_${iter}`,
    email: `k6_qa_${vuId}_${iter}_${timestamp}@testmail.com`,
    password: 'Password@123',
    title: 'Mr',
    birth_date: '16',
    birth_month: '12',
    birth_year: '2004',
    firstname: 'Pujan',
    lastname: 'Bhandari',
    company: 'QA Automation Lab',
    address1: '123 Balkumari Street',
    address2: 'Suite 4B',
    country: 'India',
    zipcode: '44600',
    state: 'Bagmati',
    city: 'Kathmandu',
    mobile_number: '9800000000',
  };
}

export function parseApiJson(res) {
  try {
    return JSON.parse(res.body);
  } catch (e) {
    return null;
  }
}

export function checkApiStatus(res, expectedCode, label) {
  const json = parseApiJson(res);
  return check(res, {
    [`${label} - HTTP 200`]: (r) => r.status === 200,
    [`${label} - API responseCode ${expectedCode}`]: () =>
      json !== null && json.responseCode === expectedCode,
  });
}
