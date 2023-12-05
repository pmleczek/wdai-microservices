from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column
from flask_marshmallow import Marshmallow

app = Flask(__name__)

app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///demo.sqlite"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

db = SQLAlchemy(app)

ma = Marshmallow(app)


class Base(DeclarativeBase):
    pass


class Person(db.Model):
    id: Mapped[int] = mapped_column(db.Integer, primary_key=True)
    name: Mapped[str] = mapped_column(db.String)
    surname: Mapped[str] = mapped_column(db.String)
    job: Mapped[str] = mapped_column(db.String)

    def __init__(self, name, surname, job):
        self.name = name
        self.surname = surname
        self.job = job


class PersonSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = Person


with app.app_context():
    db.drop_all()
    db.create_all()
    db.session.add(Person(name="ser", surname="serowy", job='it'))
    db.session.commit()

person_schema = PersonSchema()
persons_schema = PersonSchema(many=True)


@app.route('/hello')
def hello_world():
    name = request.args.get("name", None)
    return jsonify({'greeting': f'Hello, {name if name else "World"}!'})


@app.route('/person', methods=['GET', 'POST'])
def get_create_persons():
    if request.method == 'GET':
        return jsonify(persons_schema.dump(Person.query.all()))
    else:
        body = request.json
        person = Person(name=body["name"], surname=body["surname"], job=body["job"])
        db.session.add(person)
        db.session.commit()
        return jsonify(person_schema.dump(person))


@app.route('/person/<person_id>')
def get_person_by_id(person_id):
    return jsonify(person_schema.dump(Person.query.get_or_404(person_id)))


if __name__ == '__main__':
    app.run()
