import express from 'express';
import bodyParser from 'body-parser';
import indexRouter from './router/indexRouter.js';
import db from './db.js';

const app = express();

// Db test code
console.log('Connecting to the database');

try {
  await db.sequelize.sync();
  console.log('Connected to database');
} catch (error) {
  console.log(error);
}

await db.PersonSchema.create({ name: 'john', surname: 'Doe', job: 'IT' });

app.use(bodyParser.json()); 

app.use('/', indexRouter);

const PORT = 3000;

app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
});
