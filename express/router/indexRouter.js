import express from 'express';
import db from '../db.js';

const router = express.Router();

router.get('/hello', (req, res, next) => {
  res.status(200).json({ greeting: `Hello, ${req.query['name'] ?? 'World'}!` });
});

router.get('/person', async (req, res, next) => {
  const persons = await db.PersonSchema.findAll();
  res.status(200).json(persons);
});

router.get('/person/:id', async (req, res, next) => {
  const person = await db.PersonSchema.findByPk(req.params.id);
  res.status(200).json(person);
});

router.post('/person', async (req, res, next) => {
  const body = req.body;
  const result = await db.PersonSchema.create(body);
  return res.json(result.dataValues);
});

export default router;