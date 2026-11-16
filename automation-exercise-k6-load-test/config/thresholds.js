export const BASE_URL = __ENV.BASE_URL || 'https://www.automationexercise.com';

export const DEFAULT_THRESHOLDS = {
  http_req_failed: ['rate<0.02'],
  http_req_duration: ['p(95)<1500'],
  'http_req_duration{type:api}': ['p(95)<1000'],
  'http_req_duration{type:web}': ['p(95)<2000'],
};

export const LOAD_STAGES = [
  { duration: '20s', target: 10 },
  { duration: '40s', target: 10 },
  { duration: '20s', target: 25 },
  { duration: '40s', target: 25 },
  { duration: '20s', target: 0 },
];

export const STRESS_SPIKE_STAGES = [
  { duration: '15s', target: 10 },
  { duration: '15s', target: 50 },
  { duration: '30s', target: 50 },
  { duration: '15s', target: 80 },
  { duration: '20s', target: 0 },
];
