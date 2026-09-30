// @ts-check
export default {
  plugins: {
    ...(process.env.NODE_ENV === "production" ? { cssnano: {} } : {})
  }
};
/** @type {import('postcss-load-config').Config} */
